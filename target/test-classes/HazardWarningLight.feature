Feature: Hazard Warning Light

The hazard warning light is interacted through a switch that lights up both direction blinking indicators for use.
This feature defines the different pulse ratios and states for indicators when engaged and disengaged.

  Background:
    Given the hazard warning light switch is engaged

    Rule: When the flashing cycle of indicators are released, the duration of the cycle should be 1 second

    @requirement(ELS-10)
    Scenario: Engage flashing cycle of direction blinking indicators
      When the flashing cycle of indicators are released
      Then the duration of the cycle should be 1 second

      Rule: There is no change in the state of the behaviour of the indicator until the current flashing cycle is complete

    @requirement(ELS-11)
    Scenario: Engage flashing cycle of tip-blinking indicators during direction blinking
    When the flashing cycle of tip-blinking is occuring
    Then the direction blinking will start when the current cycle of tip-blinking is finished

    Rule: When the hazard warning cycle is disengaged, the indicator that corresponds to the pitarm direction should start

    @requirement(ELS-12)
    Scenario: Disengage hazard warning light with pitarm in direction blinking position
      When the hazard warning light switch is deactivated
      And pitarm is in direction blinking left
      Then the direction blinking cycle for the left indicator should start

    @requirement(ELS-12)
    Scenario: Disengage hazard warning light with pitarm in direction blinking position
      When the hazard warning light switch is deactivated
      And pitarm is in direction blinking right
      Then the direction blinking cycle for the right indicator should start








