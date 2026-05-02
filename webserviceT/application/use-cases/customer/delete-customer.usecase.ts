import type { CustomerRepository } from "../../../src/domain/repositories/customer.repository";

export class DeleteCustomerUseCase {
    constructor(private repository: CustomerRepository) {}

    async execute(id: string) {
        if (!id) {
            throw new Error("Customer code is required");
        }

        const existing = await this.repository.findById(id);

        if (!existing) {
            throw new Error("Customer not found");
        }

        return this.repository.delete(id);
    }
}