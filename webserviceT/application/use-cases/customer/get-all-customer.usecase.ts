import type { CustomerRepository } from "../../../src/domain/repositories/customer.repository";

export class GetCustomersUseCase {
    constructor(private repository: CustomerRepository) {}

    async execute() {
        const customers = await this.repository.findAll();
        
        return {
            success: true, 
            data: customers,
        };
    }
}