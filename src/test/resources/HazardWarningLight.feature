Feature: Hazard Warning Light

The hazard warning light is interacted through a switch that lights up both direction blinking indicators for use.
This feature defines the different pulse ratios and states for indicators when engaged and disengaged.

  Background:
    Given hazard warning light switch is engaged

    Rule: The duration of a flashing cycle should be a second

    @requirement(ELS-10)
    Scenario: Driver engages hazard warning light
      Then the duration of the cycle should be 1 second

  Rule: A flashing cycle must finish before a new flashing cycle starts.

    @requirement(ELS-11)
    Scenario: Driver engages tip-blinking on right while left indicator is bright
    Given ignition is on
    And the left indicator lamp is bright
    When the driver moves pitman arm upward in tip-blinking position for less than 0.5 seconds
    Then tip-blinking on right starts when direction blinking cycle finishes


  Rule: When the hazard warning cycle is disengaged, the indicator should start if pitman arm is in signalling position
+

    @requirement(ELS-12)
    Scenario: Driver deactivates hazard warning switch
      Given ignition is on
      And the driver moved pitman arm downward
      When the driver deactivates hazard warning switch
      Then the vehicle flashes all left indicators synchronously

    @requirement(ELS-12)
    Scenario: Driver deactivates hazard warning switch
      Given ignition is on
      And the driver moves pitman arm upward
      When the driver deactivates hazard warning switch
      Then the vehicle flashes all right indicators synchronously

    @requirement(ELS-12)
    Scenario: Driver disengages hazard warning switch
    Given ignition is on
    When the driver turns the ignition off
    And the driver deactivates hazard warning switch
    Then the indicators will not blink

    @requirement(ELS-12)
  Scenario: Driver engages hazard warning switch
    Given ignition is off
    When the driver engages the hazard warning switch
    Then all indicators will blink


  Rule: When the warning light is activated any tip-blinking that is ongoing will be stopped or ignored

   @requirement(ELS-13)
   Scenario: Driver engages hazard warning switch while tip-blinking right
     Given ignition is on
     And the driver moved pitman arm upward in tip-blinking position for less than 0.5 seconds
     And the driver engaged the hazard warning switch
     Then all indicators will blink








