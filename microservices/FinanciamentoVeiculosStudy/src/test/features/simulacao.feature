@SimulacaoTest
Feature: Test the basic simulation operation
  The system should provide the installments values properly.
  Following the restrictions:
  Only calculate if the values provided be greater than zero.

  Scenario Outline: Test financing simulation
    Given payload filled with vehicle value <vehicle_value> and value of entry <entry_value>, birth date <birth_date>, if has own property <has_property> and income <income>
    When I request a simulation
    Then returns the simulation data for each installment option

    Examples:
      | vehicle_value | entry_value | birth_date | has_property | income |
      | 50000         | 20000       | 01-05-1988 | 1            | 3000   |
      | 35000         | 14500       | 12-08-1995 | 0            | 4500   |
