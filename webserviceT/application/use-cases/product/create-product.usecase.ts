import { Product } from "../../../src/domain/entities/product.entity";
import type {ProductRepository} from "../../..//src/domain/repositories/product.repository";


export class CreateProductUseCase {
    constructor(private readonly repository: ProductRepository) {}

    async execute(data: any) {
        // 🔥 Reglas de negocio
        if (!data.description) {
            throw new Error("Description is required");
        }

        if (data.price < 0) {
            throw new Error("Price must be >= 0");
        }

        if (data.stock < 0) {
            throw new Error("Stock must be >= 0");
        }

        // 🔥 Crear entidad
        const product = new Product(
            data.code ?? null,
            data.description,
            data.category,
            data.price,
            data.stock,
            data.taxable
        );

        // 🔥 Persistir
        await this.repository.create(product);

        return {
            success: true,
            data: product,
        };
    }
}