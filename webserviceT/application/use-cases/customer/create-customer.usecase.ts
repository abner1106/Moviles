import type { CustomerRepository } from "../../../src/domain/repositories/customer.repository";
import type { Customer } from "../../../src/domain/entities/customer.entity";

export class CreateCustomerUseCase {
    constructor(private repository: CustomerRepository) {}

    async execute(customer: Customer) {
        // 🧠 lógica de negocio básica
        if (!customer.name) {
            throw new Error("Customer name is required");
        }

        if (!customer.email) {
            throw new Error("Customer email is required");
        }

        // podrías validar formato email aquí

        return this.repository.create(customer);
    }
}