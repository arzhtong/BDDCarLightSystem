Feature: Direction Blinking Indicator

  The pitman arm is used for controlling the direction blinking indicator. This feature is for defining positions available by the pitman arm for indicating through blinking.

    Background:
      Given the Ignition is on

     Rule: Direction blinking is only available when ignition is on.

    @requirement(ELS-1,ELS-5)
    Scenario: Engage Direction Blinking in an upward position
      When the pitman arm is moved in the upward position
      Then the vehicle flashes all right indicators synchronously

    @requirement(ELS-1,ELS-5)
    Scenario: Engage Direction Blinking in a downward position
      When the pitman arm is moved in the downward position
      Then the vehicle flashes all left indicators synchronously


    Rule: Tip-Blinking is only available when ignition is on

    @requirement(ELS-2,ELS-5)
    Scenario: Engage Tip-Blinking in an upward position
      When the pitman arm is moved in the tip-blinking upward position for less than 0.5 seconds
      Then all right indicators should flash for 3 flashing cycles

    @requirement(ELS-2,ELS-5)
    Scenario: Engage Tip-Blinking in a downward position
      When the pitman arm is moved in the tip-blinking downward position for less than 0.5 seconds (500 ms)
      Then all left indicators should flash for 3 flashing cycles

    Rule: Engage Pitman arm in another direction or engaging hazard switch will stop tip-blinking flashing cycle

    @requirement(ELS-3,ELS-5)
    Scenario: Engage Pitman Arm in a downward position from upward during tip-blinking flash cycle
      When the pitman arm is moved in a downward position from upward position during the 3 flashing cycles of tip-blinking
      Then the right indicator tip-blinking will stop
      And the requesting left indicator flashing cycle will be released

    @requirement(ELS-3,ELS-5)
    Scenario: Engage Pitman Arm in an upward position during tip-blinking flash cycle
      When the pitman arm is moved in an upward position from downward during the 3 flashing cycles of tip-blinking
      Then the left indicator tip-blinking will stop
      And the requesting right indicator flashing cycle will be released

    @requirement(ELS-3,ELS-5)
    Scenario: Engage Hazard Warning Switch during tip-blinking flash cycle
      When the hazard warning switch is engaged during the 3 flashing cycles of tip-blinking
      Then the tip-blinking will stop
      And the requested flashing cycle for the left indicator will be released
      And the same type of cycle for the right indicator will also be released

    Rule: If the pitman arm is held for more than 0.5 seconds in a tip-blinking position then direction indicators cycles are released until pitman arm leaves the position

    @requirement(ELS-4,ELS-5)
    Scenario: Engage pitman arm for more than 0.5 seconds (500 ms) in tip-blinking left
      When the pitman arm is held for more than 0.5 seconds in tip-blinking left
      Then flash-cycles are released for all direction indicators on the left until the pitman arm leaves tip-blinking left

    @requirement(ELS-4,ELS-5)
    Scenario: Engage pitman arm for more than 0.5 seconds (500 ms) in tip-blinking right
      When the pitman arm is held for more than 0.5 seconds in tip-blinking right
      Then flash-cycles are released for all direction indicators on the right until the pitman arm leaves tip-blinking right

    Rule: Cars sold in the USA or Canada will have daytime running light dimmed by 50% during direction blinking

    @requirement(ELS-6)
    Scenario: Engage direction blinking by car sold in the USA
      When direction blinking is engaged on the blinking side by a car sold in the USA
      Then the daytime running light must be dimmed by 50%

    @requirement(ELS-6)
    Scenario: Engage direction blinking by car sold in the UK
      When direction blinking is engaged on the blinking side by a car sold in the UK
      Then the daytime running light will not be dimmed for reasons related to where the car was sold

    Rule: When pitman arm is engaged in the same direction, the 3 flashing cycles must first finish before new command is processed

    @requirement(ELS-7)
    Scenario: Engage the pitman arm in an upward direction blinking position during the 3 flashing cycles where the pitman arm was already put in an upward position

      When the pitman arm is moved in an upward direction blinking position during the 3 flashing cycles where the pitman arm was in an upward position
      Then the 3 flashing cycles must finish before the right indicators start the direction blinking

    @requirement(ELS-7)
    Scenario: Engage the pitman arm in a downward tip-blinking position during the 3 flashing cycles where the pitman arm was already put in a downward position
      When the pitman arm is moved in a downward tip-blinking position during the 3 flashing cycles where the pitman arm was in a downward position
      Then the 3 flashing cycles must finish before the left indicators start the new cycle of tip-blinking








