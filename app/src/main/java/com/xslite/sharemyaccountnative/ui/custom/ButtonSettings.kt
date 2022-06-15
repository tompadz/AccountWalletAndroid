package com.xslite.sharemyaccountnative.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.xslite.sharemyaccountnative.R
import com.xslite.sharemyaccountnative.util.AndroidUtil.Companion.dynamicColorEnable
import com.xslite.sharemyaccountnative.util.AndroidUtil.Companion.pxFromDp
import com.xslite.sharemyaccountnative.util.listeners.OnToggleValueChangeListener


/**
 * Кнопка для страницы настроек,
 * имеет 2 типа, с switch button и с текстовым описанием
 * (buttonType:Switch || TextItem )
 */

class ButtonSettings : LinearLayout {

    enum class ButtonType {
        SWITCH,
        TEXT_ITEM
    }

    private var _title : String? = null
    private var _itemText : String? = null
    private var _isEnable : Boolean = false
    private var _subtitle : String? = null
    private var _buttonType : ButtonType = ButtonType.TEXT_ITEM

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private var switch : Switch? = null
    private var textItem : TextView? = null
    private var textTitle : TextView? = null
    private var textSubtitle : TextView? = null
    private var linearLayoutInfo : LinearLayout? = null

    private var _listener : OnToggleValueChangeListener? = null
    private var _clickListener : OnClickListener? = null


    var title : String?
        get() = _title
        set(value) {
            _title = value
            updateViews()
        }

    var subtitle : String?
        get() = _subtitle
        set(value) {
            _subtitle = value
            updateViews()
        }

    var isEnable : Boolean
        get() = _isEnable
        set(value) {
            _isEnable = value
            switch?.isChecked = value
            updateViews()
        }

    var buttonType : ButtonType
        get() = _buttonType
        set(value) {
            _buttonType = value
            updateViews()
        }

    var itemText : String?
        get() = _itemText
        set(value) {
            _itemText = value
            updateViews()
        }

    constructor(context : Context) : super(context) {
        init(null, 0)
    }

    constructor(context : Context, attrs : AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context : Context, attrs : AttributeSet, defStyle : Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs : AttributeSet?, defStyle : Int) {

        setRootViewTheme()

        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.ButtonSettingsToggle, defStyle, 0
        )

        _title = a.getString(
            R.styleable.ButtonSettingsToggle_title
        )
        _subtitle = a.getString(
            R.styleable.ButtonSettingsToggle_subtitle
        )
        _isEnable = a.getBoolean(
            R.styleable.ButtonSettingsToggle_enable, isEnable
        )
        _itemText = a.getString(
            R.styleable.ButtonSettingsToggle_itemText
        )
        _buttonType = a.getEnum(
            R.styleable.ButtonSettingsToggle_buttonType, buttonType
        )

        a.recycle()

        createTitle()
        createSubtitle()
        if (_buttonType == ButtonType.SWITCH) {
            createSwitch()
        } else {
            createItemText()
        }
        createInfoLayout()

        updateViews()
        addViews()
    }

    private fun setRootViewTheme() {
        this.orientation = HORIZONTAL
        this.gravity = Gravity.CENTER_VERTICAL

        this.isClickable = true

        val outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        this.setBackgroundResource(outValue.resourceId)

        this.setPadding(
            16f.pxFromDp(context),
            16f.pxFromDp(context),
            16f.pxFromDp(context),
            16f.pxFromDp(context),
        )

        this.setOnClickListener {
            if (buttonType == ButtonType.SWITCH) {
                isEnable = ! isEnable
                switch?.isChecked = isEnable
                _listener?.onChange(isEnable)
            } else {
                _clickListener?.onClick(this)
            }
        }
    }

    private fun createTitle() {
        textTitle = TextView(context)
        val typeface = ResourcesCompat.getFont(context, R.font.roboto_medium)
        textTitle?.typeface = typeface
        textTitle?.textSize = 16f
    }

    @SuppressLint("ResourceAsColor", "PrivateResource")
    private fun createItemText() {
        textItem = TextView(context)
        val typeface = ResourcesCompat.getFont(context, R.font.roboto)
        textItem?.typeface = typeface
        textItem?.setPadding(
            15f.pxFromDp(context),
            0,
            0,
            0,
        )
        if (context.dynamicColorEnable()) {
            textItem?.setTextColor(
                context.getColor(
                    com.google.android.material.R.color.m3_ref_palette_dynamic_primary60
                )
            )
        }
    }

    private fun createSubtitle() {
        textSubtitle = TextView(context)
        val typeface = ResourcesCompat.getFont(context, R.font.roboto_light)
        textSubtitle?.typeface = typeface
        textSubtitle?.textSize = 12f
    }

    @SuppressLint("PrivateResource")
    private fun createSwitch() {
        switch = Switch(context)
        switch?.isClickable = false
        switch?.isChecked = isEnable

        if (context.dynamicColorEnable()) {
            val states = arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(- android.R.attr.state_checked),
            )
            val colors = intArrayOf(
                context.getColor(com.google.android.material.R.color.m3_ref_palette_dynamic_primary60),
                Color.GRAY,
            )
            switch?.thumbTintList = ColorStateList(states, colors)
        }
    }

    private fun createInfoLayout() {
        linearLayoutInfo = LinearLayout(context)
        linearLayoutInfo?.orientation = VERTICAL
        linearLayoutInfo?.gravity = Gravity.CENTER_VERTICAL
        linearLayoutInfo?.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT,
            1f
        )
    }

    private fun addViews() {
        linearLayoutInfo?.addView(textTitle)
        if (! textSubtitle?.text.isNullOrEmpty()) {
            textSubtitle?.setPadding(0, 5f.pxFromDp(context), 0, 0)
            linearLayoutInfo?.addView(textSubtitle)
        }

        this.addView(linearLayoutInfo)

        if (buttonType == ButtonType.SWITCH) {
            this.addView(switch)
        } else {
            this.addView(textItem)
        }
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun updateViews() {
        textTitle?.text = title
        textSubtitle?.text = subtitle
        switch?.isEnabled = isEnabled
        textItem?.text = itemText
    }

    fun addOnChangeListener(listener : OnToggleValueChangeListener) {
        _listener = listener
    }

    fun addOnClickListener(listener : OnClickListener) {
        _clickListener = listener
    }

    private inline fun <reified T : Enum<T>> TypedArray.getEnum(index : Int, default : T) =
        getInt(index, - 1).let {
            if (it >= 0) enumValues<T>()[it] else default
        }
}