Feature: Gestión de productos en el sistema

  Scenario: Listar todos los productos
    Given existen productos en el sistema
    When el usuario solicita listar todos los productos
    Then se muestra una lista de todos los productos

  Scenario: Agregar un nuevo producto
    Given detalles de un nuevo producto
    When el usuario agrega el nuevo producto
    Then el producto se agrega correctamente al sistema

  Scenario: Agregar un producto que ya existe
    Given detalles de un producto existente
    When el usuario intenta agregar el producto existente
    Then la agregación del producto falla

  Scenario: Obtener detalles de un producto específico
    Given un ID de producto específico
    When el usuario solicita los detalles del producto
    Then se muestran los detalles del producto

  Scenario: Intentar agregar un producto cuando ocurre una excepción
    Given detalles de un producto y una excepción ocurre en el sistema
    When el usuario intenta agregar el producto
    Then la agregación del producto falla debido a la excepción
