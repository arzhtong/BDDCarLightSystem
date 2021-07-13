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
    When the flashing cycle of tip-blinking is occuring with the cycle being bright
    And the pitman arm is moved in the direction blinking position
    Then the direction blinking cycle will start when the direction indicator turns dark current cycle of tip-blinking is finished

    @requirement(ELS-11)
    Scenario: Engage flashing cycle of direction indicators during tip-blinking
      When the flashing cycle of direction blinking is occuring with the cycle being bright
      And the pitman arm is moved in the tip-blinking position
      Then the tip blinking cycle will start when the direction indicator turns dark and the cycle is finished

  Rule: When the hazard warning cycle is disengaged, the indicator that corresponds to the pitarm direction should start

    @requirement(ELS-12)
    Scenario: Disengage hazard warning light with pitarm in direction blinking position
      When the pitarm is in direction blinking left
      And the hazard warning light switch is deactivated
      Then the direction blinking cycle for the left indicator should start

    @requirement(ELS-12)
    Scenario: Disengage hazard warning light with pitarm in direction blinking position
      When the pitarm is in direction blinking right
      And the hazard warning light switch is deactivated
      Then the direction blinking cycle for the right indicator should start

  Rule: When the warning light is activated any tip-blinking that is ongoing will be stopped or ignored

   @requirement(ELS-13)
   Scenario: Engage hazard warning light during tip-blinking of right indicators
     Given there is a tip-blinking cycle occuring on the right direction indicator
     When the hazard warning light switch is pressed
     Then the right indicator ongoing tip-blinking will stop









