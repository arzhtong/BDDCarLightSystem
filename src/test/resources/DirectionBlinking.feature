Feature: Direction Blinking Indicator

  The pitman arm is used for controlling the direction blinking indicator. This feature is for defining positions available by the pitman arm for indicating through blinking.

  Rule: Direction blinking is only available when ignition is on.
    Background:
      Given the Ignition is on

    @requirement(ELS-1,ELS-5)
    Scenario: Engage Direction Blinking in an upward position
      When the pitman arm is moved in the upward position
      Then the vehicle flashes all right indicators synchronously

    @requirement(ELS-1,ELS-5)
    Scenario: Engage Direction Blinking in a downward position
      When the pitman arm is moved in the downward position
      Then the vehicle flashes all left indicators synchronously


    Rule: Tip-Blinking is only available when ignition is on
      Background:
        Given the Ignition is on

    @requirement(ELS-2,ELS-5)
    Scenario: Engage Tip-Blinking in an upward position
      When the pitman arm is moved in the tip-blinking upward position for less than 0.5 seconds
      Then all right indicators should flash for 3 flashing cycles

    @requirement(ELS-2,ELS-5)
    Scenario: Engage Tip-Blinking in a downward position
      When the pitman arm is moved in the tip-blinking downward position for less than 0.5 seconds
      Then all left indicators should flash for 3 flashing cycles

