package ejercicio3.tienda

import ejercicio3.tienda.model.Product
import ejercicio3.tienda.model.Customer
import ejercicio3.tienda.repository.InMemoryProductRepository
import ejercicio3.tienda.repository.InMemoryCustomerRepository
import ejercicio3.tienda.repository.InMemoryOrderRepository
import ejercicio3.tienda.service.*

fun main() {
    println("╔══════════════════════════════════════════════════════════╗")
    println("║     SISTEMA DE GESTIÓN DE VENTAS - TIENDA               ║")
    println("║            Aplicando los 5 Principios SOLID             ║")
    println("╚══════════════════════════════════════════════════════════╝")

    // ===== INICIALIZAR REPOSITORIOS =====
    val productRepo = InMemoryProductRepository()
    val customerRepo = InMemoryCustomerRepository()
    val orderRepo = InMemoryOrderRepository()

    // ===== INICIALIZAR SERVICIOS (CON DEPENDENCIAS CORRECTAS) =====
    val taxService = TaxService()

    // ValidationService solo para stock (DIP)
    val validationService = ValidationService(productRepo)

    // CartValidator para validaciones de carrito
    val cartValidator = CartValidator()

    // CartService ahora recibe los validadores que necesita
    val cartService = CartService(
        cartValidator = cartValidator,
        validationService = validationService
    )

    // StoreService recibe todo lo necesario para orquestar
    val storeService = StoreService(
        customerRepository = customerRepo,
        orderRepository = orderRepo,
        cartService = cartService,
        cartValidator = cartValidator,
        validationService = validationService,
        taxService = taxService
    )

    // HistoryService para consultas
    val historyService = HistoryService(orderRepo)

    try {
        // ===== 1. CREAR PRODUCTOS =====
        println("\n📦 AGREGANDO PRODUCTOS")
        println("─".repeat(40))

        val productos = listOf(
            Product("P001", "Laptop", 15000.0, 10),
            Product("P002", "Mouse", 250.0, 50),
            Product("P003", "Teclado", 800.0, 30),
            Product("P004", "Monitor", 3500.0, 15),
            Product("P005", "Audífonos", 600.0, 25)
        )

        productos.forEach { product ->
            productRepo.agregar(product)
            println("   ✅ ${product.nombre} - $${product.precio} (Stock: ${product.stock})")
        }

        // ===== 2. REGISTRAR CLIENTES =====
        println("\n👤 REGISTRANDO CLIENTES")
        println("─".repeat(40))

        val clientes = listOf(
            Customer(1, "Juan Pérez", "juan@email.com"),
            Customer(2, "María García", "maria@email.com")
        )

        clientes.forEach { customer ->
            customerRepo.agregar(customer)
            println("   ✅ ${customer.nombre} (${customer.email})")
        }

        // ===== 3. AGREGAR PRODUCTOS AL CARRITO =====
        println("\n🛒 AGREGANDO PRODUCTOS AL CARRITO")
        println("─".repeat(40))

        cartService.agregarAlCarrito(1, "P001", 1)  // Juan: 1 Laptop
        println("   ✅ Juan agregó: 1 Laptop")

        cartService.agregarAlCarrito(1, "P002", 2)  // Juan: 2 Mouse
        println("   ✅ Juan agregó: 2 Mouse")

        cartService.agregarAlCarrito(2, "P003", 1)  // María: 1 Teclado
        println("   ✅ María agregó: 1 Teclado")

        // ===== 4. REALIZAR COMPRAS =====
        println("\n💰 REALIZANDO COMPRAS")
        println("─".repeat(40))

        val ordenJuan = storeService.procesarCompra(1)
        println("   ✅ Compra de Juan completada:")
        println("      Subtotal: $${ordenJuan.subtotal}")
        println("      Impuestos: $${ordenJuan.impuestos}")
        println("      Total: $${ordenJuan.total}")

        val ordenMaria = storeService.procesarCompra(2)
        println("\n   ✅ Compra de María completada:")
        println("      Subtotal: $${ordenMaria.subtotal}")
        println("      Impuestos: $${ordenMaria.impuestos}")
        println("      Total: $${ordenMaria.total}")

        // ===== 5. MOSTRAR HISTORIAL =====
        println("\n📋 HISTORIAL DE COMPRAS")
        println("─".repeat(40))

        val historialJuan = historyService.obtenerHistorialCliente(1)
        println("\n👤 Juan Pérez - ${historialJuan.size} compra(s)")
        historialJuan.forEach { order ->
            println("   🧾 Orden: ${order.id.substring(0..7)}...")
            println("      Total: $${order.total}")
            println("      Productos:")
            order.items.forEach { item ->
                println("         • ${item.product.nombre} x${item.cantidad} = $${item.subtotal}")
            }
        }

        // ===== 6. DEMOSTRAR VALIDACIONES =====
        println("\n⚠️ DEMOSTRACIÓN DE VALIDACIONES")
        println("─".repeat(40))

        // Stock insuficiente (ValidationService)
        println("\n🔍 Caso 1: Stock insuficiente")
        try {
            validationService.validarStock("P001", 20)
        } catch (e: Exception) {
            println("   ❌ Error: ${e.message}")
            println("   ✅ SRP: ValidationService (solo stock)")
        }

        // Carrito vacío (CartValidator)
        println("\n🔍 Caso 2: Carrito vacío")
        try {
            val carritoVacio = cartService.obtenerCarrito(3)
            cartValidator.validarCarritoNoVacio(carritoVacio)
        } catch (e: Exception) {
            println("   ❌ Error: ${e.message}")
            println("   ✅ SRP: CartValidator (solo carrito)")
        }

        // Cantidad inválida (CartValidator)
        println("\n🔍 Caso 3: Cantidad negativa")
        try {
            cartValidator.validarCantidadPositiva(-5)
        } catch (e: Exception) {
            println("   ❌ Error: ${e.message}")
            println("   ✅ SRP: CartValidator (solo validaciones)")
        }

        // ===== 7. RESUMEN FINAL =====
        println("\n📊 RESUMEN FINAL - STOCK ACTUAL")
        println("─".repeat(40))

        productRepo.obtenerTodos().forEach { product ->
            println("   ${product.nombre}: ${product.stock} unidades")
        }

        // ===== 8. PRINCIPIOS SOLID =====
        println("\n" + "╔══════════════════════════════════════════════════════════╗")
        println("║     PRINCIPIOS SOLID APLICADOS EN LA TIENDA            ║")
        println("╠══════════════════════════════════════════════════════════╣")
        println("║  ✅ SRP: Cada servicio hace UNA cosa                    ║")
        println("║     - TaxService: solo impuestos                        ║")
        println("║     - ValidationService: solo stock                     ║")
        println("║     - CartValidator: solo validaciones de carrito      ║")
        println("║     - CartService: solo gestión de carrito              ║")
        println("║     - HistoryService: solo consultas                    ║")
        println("║     - StoreService: solo orquesta compras               ║")
        println("╠══════════════════════════════════════════════════════════╣")
        println("║  ✅ OCP: Podemos extender sin modificar                 ║")
        println("║  ✅ LSP: Repositorios intercambiables                   ║")
        println("║  ✅ ISP: Interfaces específicas                         ║")
        println("║  ✅ DIP: Dependemos de interfaces                       ║")
        println("╚══════════════════════════════════════════════════════════╝")

    } catch (e: Exception) {
        println("❌ Error: ${e.message}")
        e.printStackTrace()
    }

    println("\n✅ SISTEMA DE TIENDA FINALIZADO")
}