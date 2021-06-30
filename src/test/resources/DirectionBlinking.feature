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

    Rule: Engage Pitman arm in another direction or engaging hazard switch will stop tip-blinking flashing cycle
      Background:
        Given the Ignition is on

    Scenario: Engage Pitman Arm in a downward position from upward during tip-blinking flash cycle
      When the pitman arm is moved in a downward position from upward position during the 3 flashing cycles of tip-blinking
      Then the tip-blinking will stop and the requesting flashing cycle will be released

    Scenario: Engage Pitman Arm in an upward position during tip-blinking flash cycle
      When the pitman arm is moved in an upward position from downward during the 3 flashing cycles of tip-blinking
      Then the tip-blinking will stop and the requesting flashing cycle will be released

    Scenario: Engage Hazard Warning Switch during tip-blinking flash cycle
      When the hazard warning switch is engaged during the 3 flashing cycles of tip-blinking
      Then the tip-blinking will stop and the requesting flashing cycle will be released

