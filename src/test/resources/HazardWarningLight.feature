Feature: Hazard Warning Light

The hazard warning light is interacted through a switch that lights up both direction blinking indicators for use.
This feature defines the different pulse ratios and states for indicators when engaged and disengaged.

  Background:
    Given the hazard warning light switch is engaged

    Rule: When the flashing cycle of indicators are released, the duration of the cycle should be 1 second

    @requirement(ELS-10)
    Scenario: Driver engages hazard warning light
      When the flashing cycle of indicators are released
      Then the duration of the cycle should be 1 second

  Rule: There is no change in the state of the behaviour of the indicator until the current flashing cycle is complete

    @requirement(ELS-11)
    Scenario: Driver engages tip-blinking on right while left indicator is bright
    Given the ignition is on
    And the driver moves pitman arm downward
    And the left indicator is bright
    When the driver moves pitman arm upward in tip-blinking position
    And the pitman arm is held for less than 0.5 seconds
    Then tip-blinking on right starts when direction blinking cycle finishes


  Rule: When the hazard warning cycle is disengaged, the indicator that corresponds to the pitarm direction should start
+

    @requirement(ELS-12)
    Scenario: Driver disengages hazard warning switch with pitman arm downward
      Given the ignition is on
      And the driver engages the hazard warning switch
      When the driver moves pitman arm downward
      And the driver deactivates hazard warning switch
      Then the vehicle flashes all left indicators synchronously

    @requirement(ELS-12)
    Scenario: Driver disengages hazard warning switch with pitman arm upward
      Given the ignition is on
      And the driver engages the hazard warning switch
      When the driver moves pitman arm upward
      And the driver deactivates hazard warning switch
      Then the vehicle flashes all right indicators synchronously

    @requirement(ELS-12)
    Scenario: Driver disengages hazard warning switch with ignition off
    And the ignition is on
    When the ignition is off
    And the driver deactivates hazard warning switch
    Then the right indicator will not blink
    And the left indicator will not blink

    @requirement(ELS-12)
  Scenario: Driver engages hazard warning switch with ignition off
    Given the ignition is off
    When the driver engages the hazard warning switch
    Then the vehicle flashes all left indicators synchronously
    And the vehicle flashes all left indicators synchronously


  Rule: When the warning light is activated any tip-blinking that is ongoing will be stopped or ignored

   @requirement(ELS-13)
   Scenario: Driver engages hazard warning switch while tip-blinking right
     Given the ignition is on
     And the driver moves pitman arm upward
     And the pitman arm is held for less than 0.5 seconds
     When the driver engages the hazard warning switch
     Then the right indicator tip-blinking will stop
     And the vehicle flashes all left indicators synchronously
     And the vehicle flashes all right indicators synchronously









