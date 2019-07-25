package com.asfoundation.wallet.ui.iab

import android.os.Bundle
import io.reactivex.Observable
import java.math.BigDecimal

interface PaymentMethodsView {
  fun showPaymentMethods(paymentMethods: MutableList<PaymentMethod>, fiatValue: FiatValue,
                         currency: String)

  fun showError(message: Int)
  fun showItemAlreadyOwnedError()
  fun finish(bundle: Bundle)

  fun showLoading()
  fun hideLoading()
  fun getCancelClick(): Observable<Any>
  fun close(bundle: Bundle)
  fun errorDismisses(): Observable<Boolean>
  fun setupUiCompleted(): Observable<Boolean>
  fun showProcessingLoadingDialog()
  fun setWalletAddress(address: String)
  fun getBuyClick(): Observable<String>
  fun showPaypal()
  fun showCreditCard()
  fun showAppCoins()
  fun showCredits()
  fun showShareLink(selectedPaymentMethod: String)
  fun hideBonus()
  fun getPaymentSelection(): Observable<String>
  fun showLocalPayment(selectedPaymentMethod: String)
  fun setBonus(bonus: BigDecimal, currency: String)
  fun onBackPressed(): Observable<Boolean>
  fun showNext()
  fun showBuy()
  fun showMergedAppcoins()

  enum class SelectedPaymentMethod {
    PAYPAL, CREDIT_CARD, APPC, APPC_CREDITS, MERGED_APPC, SHARE_LINK, LOCAL_PAYMENTS, ERROR
  }
}
