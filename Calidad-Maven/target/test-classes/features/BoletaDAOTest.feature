Feature: Manejo de boletas en el sistema

  # Escenario para agregar una nueva boleta
  Scenario: Agregar una nueva boleta
    Given un cliente con dni "12345678" y nombre "Cliente de prueba"
    When el usuario agrega una nueva boleta con la fecha y hora actual
    Then la boleta se agrega correctamente al sistema

  # Escenario para listar todas las boletas
  Scenario: Listar todas las boletas
    Given boletas existentes en el sistema
    When el usuario solicita listar todas las boletas
    Then se muestra una lista de todas las boletas

  # Escenario para eliminar una boleta
  Scenario: Eliminar una boleta
    Given una boleta existente con número "1"
    When el usuario elimina la boleta con número "1"
    Then la boleta con número "1" se elimina correctamente del sistema

  # Escenario para obtener detalles de una boleta específica
  Scenario: Obtener detalles de una boleta específica
    Given una boleta existente con número "1"
    When el usuario solicita los detalles de la boleta con número "1"
    Then se muestran los detalles de la boleta con número "1"

  # Escenario para actualizar una boleta existente
  Scenario: Actualizar una boleta existente
    Given una boleta existente con número "1"
    When el usuario actualiza la boleta con número "1" con nuevos detalles
    Then la boleta con número "1" se actualiza correctamente en el sistema

 
