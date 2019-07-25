package com.asfoundation.wallet.ui.iab;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.appcoins.wallet.billing.repository.entity.TransactionData;
import com.asf.wallet.R;
import com.asfoundation.wallet.billing.adyen.PaymentType;
import com.asfoundation.wallet.entity.TransactionBuilder;
import com.asfoundation.wallet.navigator.UriNavigator;
import com.asfoundation.wallet.ui.BaseActivity;
import com.asfoundation.wallet.ui.iab.share.SharePaymentLinkFragment;
import com.jakewharton.rxrelay2.PublishRelay;
import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

import static com.appcoins.wallet.billing.AppcoinsBillingBinder.EXTRA_BDS_IAP;
import static com.asfoundation.wallet.ui.iab.WebViewActivity.SUCCESS;

/**
 * Created by franciscocalado on 20/07/2018.
 */

public class IabActivity extends BaseActivity implements IabView, UriNavigator {

  public static final String URI = "uri";
  public static final String RESPONSE_CODE = "RESPONSE_CODE";
  public static final int RESULT_USER_CANCELED = 1;
  public static final String SKU_DETAILS = "sku_details";
  public static final String APP_PACKAGE = "app_package";
  public static final String TRANSACTION_EXTRA = "transaction_extra";
  public static final String PRODUCT_NAME = "product_name";
  public static final String TRANSACTION_DATA = "transaction_data";
  public static final String TRANSACTION_HASH = "transaction_hash";
  public static final String TRANSACTION_AMOUNT = "transaction_amount";
  public static final String TRANSACTION_CURRENCY = "transaction_currency";
  public static final String DEVELOPER_PAYLOAD = "developer_payload";
  private static final String BDS = "BDS";
  private static final int WEB_VIEW_REQUEST_CODE = 1234;
  private static final String IS_BDS_EXTRA = "is_bds_extra";
  @Inject InAppPurchaseInteractor inAppPurchaseInteractor;
  private boolean isBackEnable;
  private IabPresenter presenter;
  private Bundle skuDetails;
  private TransactionBuilder transaction;
  private boolean isBds;
  private PublishRelay<Uri> results;
  private String developerPayload;
  private String uri;

  public static Intent newIntent(Activity activity, Intent previousIntent,
      TransactionBuilder transaction, Boolean isBds, String developerPayload) {
    Intent intent = new Intent(activity, IabActivity.class);
    intent.setData(previousIntent.getData());
    if (previousIntent.getExtras() != null) {
      intent.putExtras(previousIntent.getExtras());
    }
    intent.putExtra(TRANSACTION_EXTRA, transaction);
    intent.putExtra(IS_BDS_EXTRA, isBds);
    intent.putExtra(DEVELOPER_PAYLOAD, developerPayload);
    intent.putExtra(URI, intent.getData()
        .toString());
    intent.putExtra(APP_PACKAGE, transaction.getDomain());
    return intent;
  }

  public static Intent newIntent(Activity activity, String url) {
    Intent intent = new Intent(activity, IabActivity.class);
    intent.setData(Uri.parse(url));
    return intent;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    results = PublishRelay.create();
    setContentView(R.layout.activity_iab);
    isBds = getIntent().getBooleanExtra(IS_BDS_EXTRA, false);
    developerPayload = getIntent().getStringExtra(DEVELOPER_PAYLOAD);
    uri = getIntent().getStringExtra(URI);
    transaction = getIntent().getParcelableExtra(TRANSACTION_EXTRA);
    isBackEnable = true;
    presenter = new IabPresenter(this);

    if (savedInstanceState != null) {
      if (savedInstanceState.containsKey(SKU_DETAILS)) {
        skuDetails = savedInstanceState.getBundle(SKU_DETAILS);
      }
    }
    presenter.present(savedInstanceState);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == WEB_VIEW_REQUEST_CODE) {
      if (resultCode == WebViewActivity.FAIL) {
        finish();
      } else if (resultCode == SUCCESS) {
        results.accept(Objects.requireNonNull(data.getData(), "Intent data cannot be null!"));
      }
    }
  }

  @Override public void onBackPressed() {
    if (isBackEnable) {
      Bundle bundle = new Bundle();
      bundle.putInt(RESPONSE_CODE, RESULT_USER_CANCELED);
      close(bundle);
      super.onBackPressed();
    }
  }

  @Override public void disableBack() {
    isBackEnable = false;
  }

  @Override public void enableBack() {
    isBackEnable = true;
  }

  @Override public void finish(Bundle bundle) {
    setResult(Activity.RESULT_OK, new Intent().putExtras(bundle));
    finish();
  }

  @Override public void showError() {
    setResult(Activity.RESULT_CANCELED);
    finish();
  }

  @Override public void close(Bundle data) {
    Intent intent = new Intent();
    if (data != null) {
      intent.putExtras(data);
    }
    setResult(Activity.RESULT_CANCELED, intent);
    finish();
  }

  @Override public void navigateToWebViewAuthorization(String url) {
    startActivityForResult(WebViewActivity.newIntent(this, url), WEB_VIEW_REQUEST_CODE);
  }

  @Override public void showOnChain(BigDecimal amount, boolean isBds, String bonus) {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment_container, OnChainBuyFragment.newInstance(createBundle(amount),
            getIntent().getData()
                .toString(), isBds, transaction, bonus))
        .commit();
  }

  @Override public void showAdyenPayment(BigDecimal amount, String currency, boolean isBds,
      PaymentType paymentType, String bonus) {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment_container,
            AdyenAuthorizationFragment.newInstance(transaction.getSkuId(), transaction.getType(),
                getOrigin(isBds), paymentType, transaction.getDomain(), getIntent().getDataString(),
                transaction.amount(), currency, developerPayload, bonus))
        .commit();
  }

  @Override public void showAppcoinsCreditsPayment(BigDecimal appcAmount) {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment_container,
            AppcoinsRewardsBuyFragment.newInstance(appcAmount, transaction, getIntent().getData()
                .toString(), getIntent().getExtras()
                .getString(PRODUCT_NAME, ""), isBds))
        .commit();
  }

  @Override
  public void showLocalPayment(String domain, String skuId, String originalAmount, String currency,
      String bonus, String selectedPaymentMethod, String developerAddress, String type,
      BigDecimal amount, String callbackUrl, String orderReference, String payload) {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment_container,
            LocalPaymentFragment.newInstance(domain, skuId, originalAmount, currency, bonus,
                selectedPaymentMethod, developerAddress, type, amount, callbackUrl, orderReference,
                payload))
        .commit();
  }

  @Override public void showPaymentMethodsView() {
    showPaymentMethodsView(PaymentMethodsView.SelectedPaymentMethod.CREDIT_CARD);
  }

  @Override
  public void showPaymentMethodsView(PaymentMethodsView.SelectedPaymentMethod preSelectedMethod) {
    boolean isDonation = TransactionData.TransactionType.DONATION.name()
        .equalsIgnoreCase(transaction.getType());
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment_container, PaymentMethodsFragment.newInstance(transaction,
            getIntent().getExtras()
                .getString(PRODUCT_NAME), isBds, isDonation, developerPayload, uri,
            preSelectedMethod))
        .commit();
  }

  @Override public void showShareLinkPayment(String domain, String skuId, String originalAmount,
      String originalCurrency, BigDecimal amount, @NotNull String type,
      String selectedPaymentMethod) {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment_container,
            SharePaymentLinkFragment.newInstance(domain, skuId, originalAmount, originalCurrency,
                amount, type, selectedPaymentMethod))
        .commit();
  }

  @Override public void showMergedAppcoins(BigDecimal fiatAmount, String currency, String bonus,
      String productName, boolean appcEnabled, boolean creditsEnabled, boolean isBds,
      boolean isDonation) {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment_container,
            MergedAppcoinsFragment.newInstance(fiatAmount, currency, bonus, transaction.getDomain(),
                productName, transaction.amount(), appcEnabled, creditsEnabled, isBds, isDonation))
        .commit();
  }

  @Override protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    results.accept(Objects.requireNonNull(intent.getData(), "Intent data cannot be null!"));
  }

  @Override protected void onSaveInstanceState(@NotNull Bundle outState) {
    super.onSaveInstanceState(outState);

    outState.putBundle(SKU_DETAILS, skuDetails);
  }

  @Nullable private String getOrigin(boolean isBds) {
    if (transaction.getOrigin() == null) {
      return isBds ? BDS : null;
    } else {
      return transaction.getOrigin();
    }
  }

  @NonNull private Bundle createBundle(BigDecimal amount) {
    Bundle bundle = new Bundle();
    bundle.putSerializable(TRANSACTION_AMOUNT, amount);
    bundle.putString(APP_PACKAGE, transaction.getDomain());
    bundle.putString(PRODUCT_NAME, getIntent().getExtras()
        .getString(PRODUCT_NAME));
    bundle.putString(TRANSACTION_DATA, getIntent().getDataString());
    bundle.putString(DEVELOPER_PAYLOAD, transaction.getPayload());
    skuDetails = bundle;
    return bundle;
  }

  public boolean isBds() {
    return getIntent().getBooleanExtra(EXTRA_BDS_IAP, false);
  }

  @Override public void navigateToUri(String url) {
    navigateToWebViewAuthorization(url);
  }

  @Override public Observable<Uri> uriResults() {
    return results;
  }
}