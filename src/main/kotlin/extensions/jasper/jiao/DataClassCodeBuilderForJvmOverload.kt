package extensions.jasper.jiao

import extensions.wu.seal.BaseDataClassCodeBuilder
import wu.seal.jsontokotlin.model.builder.IKotlinDataClassCodeBuilder
import wu.seal.jsontokotlin.model.classscodestruct.DataClass

/**
 * kotlin class code generator with internal modifier before class
 *
 * Created by Seal on 2020/7/7 21:40.
 */
class DataClassCodeBuilderForJvmOverload(kotlinDataClassCodeBuilder: IKotlinDataClassCodeBuilder) :
    BaseDataClassCodeBuilder(kotlinDataClassCodeBuilder) {

    override fun DataClass.genPrimaryConstructor(): String {
        return buildString {
            val primaryConstructorPropertiesCode = genPrimaryConstructorProperties()
            if (primaryConstructorPropertiesCode.isNotBlank()) {
                append(" @JvmOverloads constructor(").append("\n")
                append(primaryConstructorPropertiesCode)
                append(")")
            }
        }
    }
}
