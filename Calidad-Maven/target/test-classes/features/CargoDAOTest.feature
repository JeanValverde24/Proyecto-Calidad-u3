Feature: Gestión de cargos en el sistema

  Scenario: Listar todos los cargos
    Given existen cargos en el sistema
    When el usuario solicita listar todos los cargos
    Then se muestra una lista de todos los cargos

  Scenario: Agregar un nuevo cargo
    Given un nuevo cargo con detalles específicos
    When el usuario agrega el nuevo cargo
    Then el cargo se agrega correctamente al sistema

  Scenario: Eliminar un cargo existente
    Given un cargo existente en el sistema
    When el usuario elimina el cargo
    Then el cargo se elimina correctamente del sistema

  Scenario: Obtener detalles de un cargo específico
    Given un cargo existente en el sistema
    When el usuario solicita los detalles del cargo
    Then se muestran los detalles del cargo

  Scenario: Actualizar un cargo existente
    Given un cargo existente en el sistema
    When el usuario actualiza los detalles del cargo
    Then el cargo se actualiza correctamente en el sistema

  Scenario: Obtener un cargo que no existe
    Given un ID de cargo que no existe en el sistema
    When el usuario solicita los detalles de este cargo
    Then no se encuentra ningún cargo
