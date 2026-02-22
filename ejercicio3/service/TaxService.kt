package ejercicio3.tienda.service

import ejercicio3.tienda.utils.Constants

class TaxService {

    fun calcularImpuestos(subtotal: Double): Double {
        return subtotal * Constants.TASA_IMPUESTO
    }

    fun calcularTotalConImpuestos(subtotal: Double): Double {
        return subtotal + calcularImpuestos(subtotal)
    }
}