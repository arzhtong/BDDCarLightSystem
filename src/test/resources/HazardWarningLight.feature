Feature: Hazard Warning Light

The hazard warning light is interacted through a switch that lights up both direction blinking indicators for use.
This feature defines the different pulse ratios and states for indicators when engaged and disengaged.

  Background:
    Given the hazard warning light is engaged

    @requirement(ELS-10)
    Scenario: Engage flashing cycle of direction blinking indicators
      When the flashing cycle of indicators are released
      Then the duration of the cycle should be 1 second

