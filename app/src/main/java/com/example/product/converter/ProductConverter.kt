import com.example.product.converter.ImageConverter
import com.example.product.converter.VariantConverter
import com.example.product.model.*
import com.example.product.ui.model.Image
import com.example.product.ui.model.Product
import com.example.product.ui.model.Variant
import org.modelmapper.ModelMapper

object ProductConverter {
    val mapper = ModelMapper()

    fun toModel(dto: ProductDTO): Product {
        val product = Product()
        product.id=dto.id
        product.name = dto.name
        product.category = dto.category
        product.brand = dto.brand
        product.description = dto.description
        product.status = dto.status
        product.variants = dto.variants?.let { VariantConverter.toModelList(it) } as MutableList<Variant>
        product.images = dto.images?.let { ImageConverter.toModelList(it) } as MutableList<Image>
        return product
    }

    fun toModelList(initial: List<ProductDTO>): List<Product> {
        return initial.map { toModel(it) }
    }
}
