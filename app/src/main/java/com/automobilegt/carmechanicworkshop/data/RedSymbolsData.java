package com.automobilegt.carmechanicworkshop.data;

import com.automobilegt.carmechanicworkshop.R;

public class RedSymbolsData {
    public Integer[] getSYMBOLS_ICON_ID() {
        return SYMBOLS_ICON_ID;
    }

    public String[] getSYMBOLS_NAME() {
        return SYMBOLS_NAME;
    }

    public String[] getSYMBOLS_MEANING() {
        return SYMBOLS_MEANING;
    }

    private Integer[] SYMBOLS_ICON_ID = {
            R.drawable.red_s_1_brake_trouble_1_warning, //1
            R.drawable.red_s_2_brake_trouble_2_warning, //2
            R.drawable.red_s_3_temperature_warning, //3
            R.drawable.red_s_4_low_oil_level_1_warning,  //4
            R.drawable.red_s_5_low_oil_level_2_warning,  //5
            R.drawable.red_s_6_charge_system_1_warning,  //6
            R.drawable.red_s_7_charge_system_2_warning,  //7
            R.drawable.red_s_8_seat_belt_reminder_warning,  //8
            R.drawable.red_s_9_door_ajar_warning,  //9
            R.drawable.red_s_10_master_warning,  //10
            R.drawable.red_s_11_srs_air_bag_1_warning,  //11
            R.drawable.red_s_12_srs_air_bag_2_warning,  //12
            R.drawable.red_s_13_srs_air_bag_3_warning,  //13
            R.drawable.red_s_14_security_lock_1_warning,  //14
            R.drawable.red_s_15_security_lock_2_warning,  //15
            R.drawable.red_s_16_powertrain_trouble_1_warning,  //16
            R.drawable.red_s_17_powertrain_trouble_2_warning,  //17
            R.drawable.red_s_18_transmission_temperature_1_warning,  //18
            R.drawable.red_s_19_transmission_temperature_2_warning,  //19
            R.drawable.red_s_20_park_brake_warning,  //20
            R.drawable.red_s_21_steering_trouble_warning,  //21
            R.drawable.red_s_22_service_electric_parking_brake_warning,  //22
            R.drawable.red_s_23_brake_fluid_low_warning,  //23
            R.drawable.red_s_24_safe_distance_warning,  //24
            R.drawable.red_s_25_front_end_collision_warning,  //25
            R.drawable.red_s_26_pedestrian_1_warning,  //26
            R.drawable.red_s_27_pedestrian_2_warning,  //27
            R.drawable.red_s_28_key_not_found_warning,  //28
            R.drawable.red_s_29_high_voltage_system_fault_warning,  //29
            R.drawable.red_s_30_high_volt_battery_low_warning,  //30
            R.drawable.red_s_31_external_sound_system_warning,  //31
    };

    private String[] SYMBOLS_NAME ={
            "Brake Trouble Indicator Symbol",  //1
            "Brake Trouble Indicator Symbol",  //2
            "Temperature Indicator Symbol",  //3
            "Engine Oil Level or Oil Pressure Indicator Symbol",  //4
            "Engine Oil Level or Oil Pressure Indicator Symbol",  //5
            "Charging System Trouble Indicator Symbol",  //6
            "Charging System Trouble Indicator Symbol", //7
            "Seat Belt reminder Indicator Symbol", //8
            "Door Ajar or Door Open Indicator Symbol", //9
            "Master Warning Light Indicator Symbol", //10
            "SRS Air Bag Indicator Symbol",  //11
            "SRS Air Bag Indicator Symbol",  //12
            "Air Bag Indicator Symbol",  //13
            "Security Indicator Symbol",  //14
            "Security Indicator Symbol", //15
            "Drivetrain or Powertrain Trouble Indicator Symbol",  //16
            "Drivetrain or Powertrain Trouble Indicator Symbol",  //17
            "Transmission Temperature Indicator Symbol",  //18
            "Transmission Temperature Indicator Symbol",  //19
            "Parking Brake Indicator Symbol",  //20
            "Power Steering Trouble Indicator Symbol",  //21
            "Parking Brake Trouble Indicator Symbol",  //22
            "Low Brake Fluid Indicator Symbol",  //23
            "Distance Indicator Symbol",  //24
            "Front-end Collision Indicator Symbol",  //25
            "Pedestrian Indicator Symbol",  //26
            "Pedestrian Indicator Symbol",  //27
            "Keyless Ignition Detection Indicator Symbol",  //28
            "High Voltage System Fault Indicator Symbol",  //29
            "High Voltage Battery Charge Level Indicator Symbol",  //30
            "External Sound System Fault Indicator Symbol",  //31
    };

    private String[] SYMBOLS_MEANING ={
            "Brake Trouble Warning Light could indicate a serious brake problem, but the light will also be on when the hand, or emergency brake, is engaged.\n" +
                    "Check if the hand/emergency brake is released, if you are sure it is released, bring your car to a stop as soon as possible and contact road services. \n" +
                    "Driving in this situation could be dangerous.\n",  //1
            "Brake Trouble Warning Light could indicate a serious brake problem, but the light will also be on when the hand, or emergency brake, is engaged.\n" +
                    "Check if the hand/emergency brake is released, if you are sure it is released, bring your car to a stop as soon as possible and contact road services. \n" +
                    "Driving in this situation could be dangerous.\n",  //2
            "Temperature Warning Light, indicates that the engine temperature is too high. You should stop the vehicle and turned off the engine as quickly as possible or your engine will be damaged.",  //3
            "Engine Oil Level or Oil Pressure Warning Light, indicates that the oil pressure is low. STOP driving immediately and turn the engine off. The engine can be severely damaged if oil pressure is lost. Add oil to the engine to bring the level up.\n" +
                    "If the oil level is good, do not drive the vehicle. Call road service, driving with no or low oil pressure will damage your engine.\n",  //4
            "Engine Oil Level or Oil Pressure Warning Light, indicates that the oil pressure is low. STOP driving immediately and turn the engine off. The engine can be severely damaged if oil pressure is lost. Add oil to the engine to bring the level up.\n" +
                    "If the oil level is good, do not drive the vehicle. Call road service, driving with no or low oil pressure will damage your engine.\n",  //5
            "Charging System Trouble Warning Light indicates a Charging System problem. The vehicle should be stop as quickly as possible or the vehicle may simply stop running on its own. \n" +
                    "Roadside assistance will be necessary.\n" +
                    "If other lights are on, the Charging System is the priority. The other lights will likely resolve themselves once the Charging System is repaired.\n",  //6
            "Charging System Trouble Warning Light indicates a Charging System problem. The vehicle should be stop as quickly as possible or the vehicle may simply stop running on its own. \n" +
                    "Roadside assistance will be necessary.\n" +
                    "If other lights are on, the Charging System is the priority. The other lights will likely resolve themselves once the Charging System is repaired.\n",  //7
            "Seat Belt Reminder Warning Light, reminder, you have to put on your seat belt.",  //8
            "Door Ajar or Door Open Warning Light indicates that the door, trunk lid, rear hatch, hood, or whatever is not close. Close the opening door and the automobile will operate normally.",  //9
            "Master Warning Light. usually accompanied by another warning light, and indicates that one or more warning systems have been detected. The Master Warning Light ranges in its levels of significance and severity. It is important to check your vehicle before operating it.",  //10
            "Supplemental Restraint System (SRS) Air Bag Warning Light, indicates that a problem exists with one of the many airbags in the automobile, which should be taken to the dealer or authorized service center as soon as possible.",  //11
            "Supplemental Restraint System (SRS) Air Bag Warning Light, indicates that a problem exists with one of the many airbags in the automobile, which should be taken to the dealer or authorized service center as soon as possible.",  //12
            "Air Bag Warning Light indicates that a problem exists with one of the many airbags in the vehicle, which should be taken to the dealer or authorized service center as soon as possible.",  //13
            "Security Warning Light, indicates that the ignition switch is locked and will need the proper key to start or re-start.\n" +
                    "If it visible when the vehicle is running, the symbol indicates a malfunction in the security system.the vehicle will operate normally, and your dealer or qualifier repair shop will need to repair the security system.\n",  //14
            "Security Warning Light, indicates that the ignition switch is locked and will need the proper key to start or re-start.\n" +
                    "If it visible when the vehicle is running, the symbol indicates a malfunction in the security system.the vehicle will operate normally, and your dealer or qualifier repair shop will need to repair the security system.\n",  //15
            "Drivetrain or Powertrain Trouble Warning Light, It indicates a problem has been detected generally in the automatic transmission or transaxle, or its shift system. It should be serviced as soon as possible. \n" +
                    "If you are in motion, you should stop the car and call for a road service. Additional damage could be done to your drivetrain if you continue driving.\n",  //16
            "Drivetrain or Powertrain Trouble Warning Light, It indicates a problem has been detected generally in the automatic transmission or transaxle, or its shift system. It should be serviced as soon as possible. \n" +
                    "If you are in motion, you should stop the car and call for a road service. Additional damage could be done to your drivetrain if you continue driving.\n",  //17
            "Transmission Temperature Warning Light, indicates that the oil temperature in the transmission is too high, which risks transmission  failure.",  //18
            "Transmission Temperature Warning Light, indicates that the oil temperature in the transmission is too high, which risks transmission  failure.",  //19
            "Parking Brake Warning Light, indicates that the parking brake is engaged. Be sure to disengage the parking or emergency brake before driving.",  //20
            "Power Steering Trouble Warning Light, If it is an electronic system, there is a chance it can be “reset”. Pull over as soon as possible and shut down the engine and restart it. If the light does not go out you will need to take the vehicle to your dealer or authorized repair facility.",  //21
            "Parking Brake Trouble Warning Light, used with Electronic Parking Brakes, indicates that it needs to be serviced. \n" +
                    "The car can be driven, but be sure to have it serviced as soon as possible.\n",  //22
            "Low Brake Fluid Warning Light indicates that the brake fluid is low. Check the brake fluid level and add fluid appropriate to the vehicle make and model.\n" +
                    "It is important to note that the brake fluid level will drop as the brake pads wear down, or due to leakge. Use this light as a sign that your car should be checked for important brake maintenance.\n",  //23
            "Distance Warning Light, It is a warning that an automobile in front is too close or is being approached too quickly, or that a stationary obstacle exists in the direction of travel.",  //24
            "Front-end Collision Warning Light, it is a warning that the system has a problem or if a collision is imminent. We’ve shown it in red, which is used when a potential collision is detected. It will flash red as the condition worsens and/or when the relative speed between the two vehicles is great. although the symbol is that of the back-end of a car. The manufacturer uses the same symbol for both Front and End.",  //25
            "Pedestrian Warning Light, It is a warning that a collision with a person detected is imminent.",  //26
            "Pedestrian Warning Light, It is a warning that a collision with a person detected is imminent.",  //27
            "Keyless Ignition Detection Warning Light indicates that the key is not detected. If the Key is in the vehicle, its internal battery is likely dead and needs to be replaced.",  //28
            "High Voltage System Fault Warning Light is found on the instrument panels of plug-in hybrid vehicles and is illuminated when there is a problem in system, which must be addressed by your dealer.",  //29
            "High Voltage Battery Charge Level Warning Light, is found on the instrument panels of plug-in hybrid vehicles and is illuminated when the main battery’s charge is low. The vehicle needs to be plugged into a charging station or the gasoline engine needs to be operated to recharge.",  //30
            "External Sound System Fault Warning Light, is used by electric vehicles (EVs) or hybrids operating in EV mode to alert pedestrians to the presence of the vehicle. This light, showing curved lines emanating from a car, indicates a problem with system, which must be addressed by your dealer.",  //31
    };
}
