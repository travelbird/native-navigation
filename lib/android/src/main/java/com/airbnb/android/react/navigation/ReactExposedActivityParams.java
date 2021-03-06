package com.airbnb.android.react.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import com.facebook.react.bridge.ReadableMap;
import rodo.Rodo;

import static com.airbnb.android.react.navigation.ReactExposedActivityParamsConstants.KEY_ARGUMENT;

public class ReactExposedActivityParams {
  private final String key;
  private final Class<? extends Activity> klass;
  private final Class<? extends Parcelable> argumentType;
  private static final Class<Bundle> DEFAULT_CLASS = Bundle.class;

  public ReactExposedActivityParams(String key, Class<? extends Activity> klass) {
    this(key, klass, DEFAULT_CLASS);
  }

  public ReactExposedActivityParams(String key, Class<? extends Activity> klass,
      Class<? extends Parcelable> argumentType) {
    this.key = key;
    this.klass = klass;
    this.argumentType = argumentType;
  }

  /**
   * Converts the provided {@link ReadableMap} arguments into an {@link Intent} used for launching
   * the {@link Activity} associated with this object. The {@code arguments} will be used as {@link
   * Intent} {@code extras} and converted according to the {@code argumentType} field. By default,
   * all activities will take a {@link Bundle} extra, however, if a custom {@code argumentType}
   * class is provided, the {@code arguments} will be automatically converted into an object of the
   * type {@code argumentType} instead, by using Jackson to deserialize the contents of {@code
   * arguments}.
   */
  Intent toIntent(Context context, Rodo rodo, ReadableMap arguments) {
    Intent intent = new Intent(context, klass);
    if (argumentType.equals(DEFAULT_CLASS)) {
      intent.putExtras(ConversionUtil.toBundle(arguments));
    } else {
      intent.putExtra(KEY_ARGUMENT, rodo.convert(argumentType, arguments));
    }
    return intent;
  }

  String key() {
    return key;
  }
}
