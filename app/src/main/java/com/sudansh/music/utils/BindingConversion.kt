package com.sudansh.music.utils

import android.view.View
import androidx.databinding.BindingConversion

@BindingConversion
fun booleanToVisibility(isVisible: Boolean): Int = if (isVisible) View.VISIBLE else View.INVISIBLE