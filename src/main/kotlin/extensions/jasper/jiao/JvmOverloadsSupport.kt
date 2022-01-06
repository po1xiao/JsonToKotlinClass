package extensions.jasper.jiao

import extensions.Extension
import wu.seal.jsontokotlin.model.ConfigManager
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.classscodestruct.DataClass
import wu.seal.jsontokotlin.model.classscodestruct.KotlinClass
import wu.seal.jsontokotlin.ui.jCheckBox
import wu.seal.jsontokotlin.ui.jHorizontalLinearLayout
import javax.swing.JPanel

/**
 * @author Jasper Jiao
 * create at 2022/1/5
 * description:
 * 为data class提供了默认无参的构造方法（并且有初始值），需要通过DefaultValueStrategy.AvoidNull来配合生效。
 *
 * default:
 *
 *     data class Foo(
 *         val a: Int // 1
 *     )
 *
 *
 * after enable this:
 *
 *     data class Foo @JvmOverloads constructor(
 *         val a: Int = 0 // 1
 *     )
 *
 */
object JvmOverloadsSupport : Extension() {
    /**
     * Config key can't be private, as it will be accessed from `library` module
     */
    @Suppress("MemberVisibilityCanBePrivate")
    const val configKey = "jasper.jiao.add_jvm_overload_annotation_enable"
    override fun createUI(): JPanel {
        return jHorizontalLinearLayout {
            jCheckBox("Add JvmOverloads Annotation For Constructor",
                getConfig(configKey).toBoolean(), { isSelected ->
                    setConfig(configKey, isSelected.toString())
                    ConfigManager.defaultValueStrategy = DefaultValueStrategy.AvoidNull
                })
            fillSpace()
        }
    }

    override fun intercept(kotlinClass: KotlinClass): KotlinClass {
        if (getConfig(configKey).toBoolean())
            if (kotlinClass is DataClass) {
                return kotlinClass.copy(codeBuilder = DataClassCodeBuilderForJvmOverload(kotlinClass.codeBuilder))
            }
        return kotlinClass
    }
}