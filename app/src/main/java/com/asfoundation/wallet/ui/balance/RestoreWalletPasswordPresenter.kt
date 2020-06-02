package com.asfoundation.wallet.ui.balance

import com.asfoundation.wallet.billing.analytics.WalletsAnalytics
import com.asfoundation.wallet.billing.analytics.WalletsEventSender
import com.asfoundation.wallet.interact.WalletModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class RestoreWalletPasswordPresenter(private val view: RestoreWalletPasswordView,
                                     private val activityView: RestoreWalletActivityView,
                                     private val interactor: RestoreWalletPasswordInteractor,
                                     private val walletsEventSender: WalletsEventSender,
                                     private val disposable: CompositeDisposable,
                                     private val viewScheduler: Scheduler,
                                     private val networkScheduler: Scheduler,
                                     private val computationScheduler: Scheduler) {

  fun present(keystore: String) {
    populateUi(keystore)
    handleRestoreWalletButtonClicked(keystore)
  }

  private fun populateUi(keystore: String) {
    disposable.add(interactor.extractWalletAddress(keystore)
        .subscribeOn(networkScheduler)
        .flatMap { address ->
          interactor.getOverallBalance(address)
              .observeOn(viewScheduler)
              .doOnSuccess { fiatValue -> view.updateUi(address, fiatValue) }
        }
        .subscribe())
  }

  private fun handleRestoreWalletButtonClicked(keystore: String) {
    disposable.add(view.restoreWalletButtonClick()
        .doOnNext {
          activityView.hideKeyboard()
          view.showWalletRestoreAnimation()
        }
        .doOnNext {
          walletsEventSender.sendWalletPasswordRestoreEvent(WalletsAnalytics.ACTION_IMPORT,
              WalletsAnalytics.STATUS_SUCCESS)
        }
        .doOnError {
          walletsEventSender.sendWalletPasswordRestoreEvent(WalletsAnalytics.ACTION_IMPORT,
              WalletsAnalytics.STATUS_FAIL, it.message)
        }
        .observeOn(computationScheduler)
        .flatMapSingle { interactor.restoreWallet(keystore, it) }
        .observeOn(viewScheduler)
        .doOnNext { handleWalletModel(it) }
        .doOnError {
          walletsEventSender.sendWalletCompleteRestoreEvent(WalletsAnalytics.STATUS_FAIL, it.message)
        }
        .subscribe())
  }

  private fun setDefaultWallet(address: String) {
    disposable.add(interactor.setDefaultWallet(address)
        .doOnComplete { view.showWalletRestoredAnimation() }
        .subscribe())
  }

  private fun handleWalletModel(walletModel: WalletModel) {
    if (walletModel.error.hasError) {
      view.hideAnimation()
      view.showError(walletModel.error.type)
      walletsEventSender.sendWalletCompleteRestoreEvent(WalletsAnalytics.STATUS_FAIL,
          walletModel.error.type.toString())
    } else {
      setDefaultWallet(walletModel.address)
      walletsEventSender.sendWalletCompleteRestoreEvent(WalletsAnalytics.STATUS_SUCCESS)
    }
  }

  fun stop() {
    disposable.clear()
  }
}
