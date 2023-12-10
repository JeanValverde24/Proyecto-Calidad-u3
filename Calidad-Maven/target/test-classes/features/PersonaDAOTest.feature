Feature: Gestión de personas en el sistema

  Scenario: Listar todas las personas
    Given existen personas en el sistema
    When el usuario solicita listar todas las personas
    Then se muestra una lista de todas las personas

  Scenario: Agregar una nueva persona
    Given detalles de una nueva persona
    When el usuario agrega la nueva persona
    Then la persona se agrega correctamente al sistema

  Scenario: Agregar una persona que ya existe
    Given detalles de una persona existente
    When el usuario intenta agregar la persona existente
    Then la agregación de la persona falla

  Scenario: Actualizar detalles de una persona
    Given una persona existente en el sistema
    When el usuario actualiza los detalles de la persona
    Then los detalles de la persona se actualizan correctamente

  Scenario: Eliminar una persona existente
    Given una persona existente en el sistema
    When el usuario elimina la persona
    Then la persona se elimina correctamente del sistema

  Scenario: Iniciar sesión con credenciales válidas
    Given credenciales válidas de una persona
    When la persona intenta iniciar sesión
    Then el inicio de sesión es exitoso

  Scenario: Iniciar sesión con credenciales inválidas
    Given credenciales inválidas de una persona
    When la persona intenta iniciar sesión
    Then el inicio de sesión falla
