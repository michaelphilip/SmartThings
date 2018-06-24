/*
 *  Leviton VRCZ4-M0Z Zone Controller
 *
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
 
 	metadata {
	// Automatically generated. Make future change here.
	definition (name: "Leviton VRCZ4-M0Z Zone Controller", namespace: "mpk", author: "Michael Philip Kaufman") {
		capability "Actuator"
        capability "Button"
        capability "Configuration"
        capability "Sensor"
        
        attribute "associatedLoad", "STRING"
        attribute "associatedLoadId", "STRING"
        attribute "currentButton", "STRING"
        attribute "numButtons", "STRING"
        command "associateLoad", ["NUMBER"]
        command "setButton", ["NUMBER", "STRING"]
 		command "getparamState"       
 		command "setLightStatus"       
        
		fingerprint deviceId: "0x0100", inClusters:"0x85, 0x2D, 0x7C, 0x77, 0x82, 0x73, 0x86, 0x72, 0x91, 0xEF, 0x2B, 0x2C"
        // fingerprint zw:L type:0100 mfr:001D prod:0702 model:0261 ver:0.02 zwv:2.97 lib:01
        // fingerprint mfr: "001D", prod: "0702", model: "0261"
    }

   	/*preferences {
            input "button1", "string", title: "Button 1 Network id", description: "button 1 network id", required: false
            input "button2", "string", title: "Button 2 Network id", description: "button 2 network id", required: false
            input "button3", "string", title: "Button 3 Network id", description: "button 3 network id", required: false
            input "button4", "string", title: "Button 4 Network id", description: "button 4 network id", required: false
     }*/
	simulator {
		status "button 1 pushed":  "command: 2B01, payload: 01 FF"
		status "button 2 pushed":  "command: 2B01, payload: 02 FF"
		status "button 3 pushed":  "command: 2B01, payload: 03 FF"
		status "button 4 pushed":  "command: 2B01, payload: 04 FF"
    	status "button released":  "command: 2C02, payload: 00"
	}

	tiles {
		standardTile("button", "device.button", width: 6, height: 4) {
			state "default", label: " ", icon: "http://schemalive.com/vrcz4-m0z.png", backgroundColor: "#ffffff"
		}
        
        // Configure button.  Syncronize the device capabilities that the UI provides
		standardTile("configure", "device.configure", inactiveLabel: false, decoration: "flat") {
			state "configure", label:'', action:"configuration.configure", icon:"st.secondary.configure"
		}
      	standardTile("getmyState", "device.switch", height: 2, width: 2, inactiveLabel: false, decoration: "flat") {
        	state "default", label:"Get My State", action:"getparamState"
		}

		main "button"
		details (["button", "configure", "getmyState"])
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
	if (description.contains("command: 9100")) {
    	def result = handleManufacturerProprietary(description)
        log.debug "${result}"
    	return result
    }

    def result = null
    def cmd = zwave.parse(description)
    // def cmd = zwave.parse(description, [0x91: 1])
    if (cmd) {
        result = zwaveEvent(cmd)
        log.debug "Parsed ${cmd} to ${result.inspect()} (${result}) from description ${description}"
    } else {
        log.debug "Non-parsed event: ${description}"
    }
    return result
}

def handleManufacturerProprietary(String description) {
	// log.debug "Handling manufacturer-proprietary command: '${description}'"
    def result = []
    
	switch (description) {
    	case "zw device: ${device.deviceNetworkId}, command: 9100, payload: 1D 0C 01 00 ":
        case "1":
        	// log.debug "Turning button #1 on"
            updateState("currentButton", "1")
            result << createEvent(name: "button", value: "on", data: [button: 1, status: "on"], isStateChange: true)
        	break
    	case "zw device: ${device.deviceNetworkId}, command: 9100, payload: 1D 0C 05 00 ":
        case "5":
        	// log.debug "Turning button #1 off"
            updateState("currentButton", "1")
            result << createEvent(name: "button", value: "off", data: [button: 1, status: "off"], isStateChange: true)
        	break
    	case "zw device: ${device.deviceNetworkId}, command: 9100, payload: 1D 0C 02 00 ":
        case "2":
        	// log.debug "Turning button #2 on"
            updateState("currentButton", "2")
            result << createEvent(name: "button", value: "on", data: [button: 2, status: "on"], isStateChange: true)
        	break
    	case "zw device: ${device.deviceNetworkId}, command: 9100, payload: 1D 0C 06 00 ":
        case "6":
        	// log.debug "Turning button #2 off"
            updateState("currentButton", "2")
            result << createEvent(name: "button", value: "off", data: [button: 2, status: "off"], isStateChange: true)
        	break
    	case "zw device: ${device.deviceNetworkId}, command: 9100, payload: 1D 0C 03 00 ":
        case "3":
        	// log.debug "Turning button #3 on"
            updateState("currentButton", "3")
            result << createEvent(name: "button", value: "on", data: [button: 3, status: "on"], isStateChange: true)
        	break
    	case "zw device: ${device.deviceNetworkId}, command: 9100, payload: 1D 0C 07 00 ":        
        case "7":
        	// log.debug "Turning button #3 off"
            updateState("currentButton", "3")
            result << createEvent(name: "button", value: "off", data: [button: 3, status: "off"], isStateChange: true)
        	break
    	case "zw device: ${device.deviceNetworkId}, command: 9100, payload: 1D 0C 04 00 ":
        case "4":
        	// log.debug "Turning button #4 on"
            updateState("currentButton", "4")
            result << createEvent(name: "button", value: "on", data: [button: 4, status: "on"], isStateChange: true)
        	break
    	case "zw device: ${device.deviceNetworkId}, command: 9100, payload: 1D 0C 08 00 ":
        case "8":
        	// log.debug "Turning button #4 off"
            updateState("currentButton", "4")
            result << createEvent(name: "button", value: "off", data: [button: 4, status: "off"], isStateChange: true)
        	break
        default:
        	// log.debug "Fell through to default switch case"
        	break
    }
    
    return result
}

def setButton(num, val) {
	log.debug "Setting button${num} to ${val}"
	updateState("button$num", val)
}

// Handle a button being pressed
def buttonEvent(button) {
    // log.debug "Entering buttonEvent() for button ${button}..."
	button = button as Integer
	def result = []
       
    // updateState("currentButton", "$button")
    // handleManufacturerProprietary(button.toString())
        
            
    if (button > 0) {    
        // update the device state, recording the button press
        // log.debug "${device.displayName} button ${button} was pushed"
        // log.debug "Turning button #${buttonToSwitch(button)} ${buttonToStatus(button)}"
        updateState("currentButton", buttonToSwitch(button))
        result << createEvent(name: "button", value: "off", data: [button: buttonToSwitch(button), status: buttonToStatus(button)], isStateChange: true)
        // result << createEvent(name: "button", value: /*"pushed"*/ "button $button", data: [buttonNumber: button], descriptionText: "$device.displayName button $button was pushed", isStateChange: true)
	}
    else {    
        // update the device state, recording the button press
        // log.debug "${device.displayName} button ${button} was released"
        result << createEvent(name: "button", value: "default", descriptionText: "$device.displayName button was released", isStateChange: true)        
    }
    
    return result
}

// Map button on/off to switch #
def buttonToSwitch(button)
{
	return ((button < 5) ? button : button - 4).toString()
}

// Map button on/off to status
def buttonToStatus(button)
{
	return (button < 5) ? "on" : "off"
}

def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicReport cmd)
{
	// log.debug "BasicReport: ${cmd}"
    def result = []
    result << createEvent(name:"switch", value: cmd.value ? "on" : "off")

    // For a multilevel switch, cmd.value can be from 1-99 to represent dimming levels
    result << createEvent(name:"level", value: cmd.value, unit:"%", descriptionText:"${device.displayName} dimmed ${cmd.value==255 ? 100 : cmd.value}%")

    return result
}

def zwaveEvent(physicalgraph.zwave.commands.switchbinaryv1.SwitchBinaryReport cmd) {
	// log.debug "SwitchBinaryReport: ${cmd}"
    createEvent(name:"switch", value: cmd.value ? "on" : "off")
}

def zwaveEvent(physicalgraph.zwave.commands.switchmultilevelv3.SwitchMultilevelReport cmd) {
	// log.debug "SwitchMultilevelReport: ${cmd}"
    def result = []
    result << createEvent(name:"switch", value: cmd.value ? "on" : "off")
    result << createEvent(name:"level", value: cmd.value, unit:"%", descriptionText:"${device.displayName} dimmed ${cmd.value==255 ? 100 : cmd.value}%")
    
    return result
}

def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicSet cmd) {
	// log.debug "$device.displayName bzone button was pressed: ${cmd}"
    createEvent(name: "button", value: "default", descriptionText: "$device.displayName bzone button was pressed", isStateChange: true)
}

def zwaveEvent(physicalgraph.zwave.commands.sceneactivationv1.SceneActivationSet cmd) {
	// log.debug "Entering zwaveEvent handler for SceneActivationSet with cmd = ${cmd}"
	// The controller likes to repeat the command... ignore repeats
	if (state.lastScene == cmd.sceneId && (state.repeatCount < 4) && (now() - state.repeatStart < 2000)) {
    	// log.debug "Button ${cmd.sceneId} repeat ${state.repeatCount}x ${now()}"
        state.repeatCount = state.repeatCount + 1
        createEvent([:])
    }
    else {
    	// If the button was really pressed, store the new scene and handle the button press
        state.lastScene = cmd.sceneId
        state.lastLevel = 0
        state.repeatCount = 0
        state.repeatStart = now()

        buttonEvent(cmd.sceneId)
    }
}

// A scene command was received -- it's probably scene 0, so treat it like a button release
def zwaveEvent(physicalgraph.zwave.commands.sceneactuatorconfv1.SceneActuatorConfGet cmd) {
	// log.debug "SceneActuatorConfGet: $cmd"
	buttonEvent(cmd.sceneId)
}

def zwaveEvent(physicalgraph.zwave.commands.sceneactuatorconfv1.SceneActuatorConfReport cmd) {
    // log.debug "SceneActuatorConfReport: ${cmd}"
	// log.debug "Scene ${cmd.sceneId} set to ${cmd.level}"
    
    createEvent([:])
}

def zwaveEvent(physicalgraph.zwave.commands.scenecontrollerconfv1.SceneControllerConfReport cmd) {
    // log.debug "Scene controller report: ${cmd}"
	// log.debug "Group ${cmd.groupId} set to Scene ${cmd.sceneId}"
    
    // createEvent([:])
}

// Configuration Reports are replies to configuration value requests... If we knew what configuration parameters
// to request this could be very helpful.
def zwaveEvent(physicalgraph.zwave.commands.configurationv1.ConfigurationReport cmd) {
	// log.debug "Received configuration report ${cmd}"
	createEvent([:])
}

// The VRC supports hail commands, but I haven't seen them.
def zwaveEvent(physicalgraph.zwave.commands.hailv1.Hail cmd) {
	// log.debug "Hail command received" 
    createEvent([name: "hail", value: "hail", descriptionText: "Switch button was pressed", displayed: false])
}

// Update manufacturer information when it is reported
def zwaveEvent(physicalgraph.zwave.commands.manufacturerspecificv2.ManufacturerSpecificReport cmd) {
	// log.debug "Manufacturer-specific report: ${cmd}"
	if (state.manufacturer != cmd.manufacturerName) {
		updateDataValue("manufacturer", cmd.manufacturerName)
	}
    
    createEvent([:])
}

// Association Groupings Reports tell us how many groupings the device supports.  
// This equates to the number of buttons/scenes in the VRCZ
def zwaveEvent(physicalgraph.zwave.commands.associationv2.AssociationGroupingsReport cmd) {
	// log.debug "Association grouping report: ${cmd}" 
	def response = []
    
    // log.debug "${getDataByName("numButtons")} buttons stored"
	if (getDataByName("numButtons") != "$cmd.supportedGroupings") {
		updateState("numButtons", "$cmd.supportedGroupings")
        // log.debug "${cmd.supportedGroupings} groups available"
        response << createEvent(name: "numButtons", value: cmd.supportedGroupings, displayed: false)
        
        response << associateHub()
	}    
    else { 	
    	response << createEvent(name: "numButtons", value: cmd.supportedGroupings, displayed: false)
    }
    
    return response
}

def zwaveEvent(physicalgraph.zwave.commands.associationv2.AssociationReport cmd) {
	// log.debug "Association report V2: ${cmd}" 
	def commands = []
    def buttonlist = ['x', state.button1.split(','), state.button2.split(','), state.button3.split(','), state.button4.split(',')]
    // log.debug "buttonlist: ${buttonlist}"
    cmd.nodeId.each({// log.debug "AssociationReport: '${cmd}', hub: '$zwaveHubNodeId' reports nodeId: '$it' is associated in group: '${cmd.groupingIdentifier}'"
    	if((it != zwaveHubNodeId) && (!buttonlist[integer(cmd.groupingIdentifier)].contains(Integer.toHexString(it).toUpperCase())))
        {
        	 // log.debug "$it couldn't find " + Integer.toHexString(it).toUpperCase() + "in button ${cmd.groupingIdentifier}"
        	 sendHubCommand(new physicalgraph.device.HubAction(zwave.associationV2.associationRemove(groupingIdentifier: cmd.groupingIdentifier, nodeId: it).format()))
        }
    })
    [:]
}

def zwaveEvent(physicalgraph.zwave.commands.associationv1.AssociationReport cmd) {
	// log.debug "Association report V1: ${cmd}" 
	def commands = []
    cmd.nodeId.each({// log.debug "AssociationReport v1: '${cmd}', hub: '$zwaveHubNodeId' reports nodeId: '$it' is associated in group: '${cmd.groupingIdentifier}'"
        			 commands << zwave.associationV1.associationRemove(groupingIdentifier: cmd.groupingIdentifier, nodeId: it).format()
    })
    // log.debug "Sending $commands"
    return delayBetween(commands,100)
    //[:]
}

def zwaveEvent(physicalgraph.zwave.commands.switchmultilevelv3.SwitchMultilevelStartLevelChange cmd) {	
	// log.debug "Switch Multilevel Start Level Change v3: ${cmd}" 
    createEvent(name: "button", value: "on", data: [button: 0, status: "start", switch: "${state.currentButton}", direction: cmd.upDown == 1 ? "up" : "down"], isStateChange: true)
}

def zwaveEvent(physicalgraph.zwave.commands.switchmultilevelv3.SwitchMultilevelStopLevelChange cmd) {	
	// log.debug "Switch Multilevel Stop Level Change v3: ${cmd}" 
    createEvent(name: "button", value: "off", data: [button: 0, status: "stop", switch: "${state.currentButton}"], isStateChange: true)
}

// Handles all Z-Wave commands we don't know we are interested in
def zwaveEvent(physicalgraph.zwave.Command cmd) {	
	// log.debug "Default zwaveEvent handler: ${cmd}" 
    createEvent(descriptionText: "${device.displayName}: ${cmd}")
    // createEvent([:])
}

// handle commands

// Create a list of the configuration commands to send to the device
def configurationCmds() {
	// Always check the manufacturer and the number of groupings allowed
	def commands = [
    	zwave.manufacturerSpecificV2.manufacturerSpecificGet().format(),
		zwave.associationV1.associationGroupingsGet().format()
    ]
    
    commands = commands + associateHub()

    // Reset to sceneId 0 (no scene) initially to turn off all LEDs.
    // commands << zwave.sceneActuatorConfV1.sceneActuatorConfReport(dimmingDuration: 255, level: 255, sceneId: 0).format()
    // commands << zwave.sceneActuatorConfV1.sceneActuatorConfReport(dimmingDuration: 0, level: 255, sceneId: 1).format()
    // commands << zwave.sceneActuatorConfV1.sceneActuatorConfReport(dimmingDuration: 0, level: 255, sceneId: 2).format()
    // commands << zwave.sceneActuatorConfV1.sceneActuatorConfReport(dimmingDuration: 0, level: 255, sceneId: 3).format()
    // commands << zwave.sceneActuatorConfV1.sceneActuatorConfReport(dimmingDuration: 0, level: 255, sceneId: 4).format()

	// log.debug "configurationCmds: ${commands}"
    delayBetween(commands)
}

def getparamState() {
	// log.debug "Listing current parameter/attribute settings for ${device.displayName}..."
    def cmds = []
    sendHubCommand(new physicalgraph.device.HubAction("91001D0D01FF01180508D3"))
    // log.debug "Sending $cmds to ${device.deviceNetworkId}..."
    // log.debug "deviceNetworkId: ${device.deviceNetworkId}"
    // log.debug "displayName: ${device.displayName}"
    // log.debug "numButtons: ${state.numButtons}"
    // log.debug "currentButton: ${state.currentButton}"
    // log.debug "associatedLoad: ${associatedLoad}"
    // log.debug "associatedLoadId: ${associatedLoadId}"
    // log.debug "capabilityCommands = ${getDeviceCapabilityCommands(device.capabilities)}"    
}

def setLightStatus(one,two,three,four)
{
    def hidden = ["0F", "00", "13", device.deviceNetworkId ]
    def start = ["91", "00", "1D", "0D", "01", "FF"]
    def end = ["00", "00", "0A"]
    def light = integer(one) + (integer(two) << 1) + (integer(three) << 2) + (integer(four) << 3)
    log.debug "Setting button-light statuses for ${device.displayName}..."
	log.debug "one,two,three,four = $one,$two,$three,$four"
    log.debug "HIDDEN: $hidden"
    log.debug "START: $start"
    log.debug "END: $end"
    log.debug "LIGHT: $light"
    def checksum = 255
    hidden.each { checksum^=(integerhex(it)) }
    log.debug "checksum, post-hidden: $checksum"
    start.each { checksum^=(integerhex(it)) }
    log.debug "checksum, post-start: $checksum"
    end.each { checksum^=(integerhex(it)) }
    log.debug "checksum, post-end: $checksum"    
    checksum^=light
    log.debug "checksum, post-light: $checksum"    
    log.debug "${start.join()}${String.format("%02X",light)}${end.join()}${String.format("%02X",checksum)}"
    sendHubCommand(new physicalgraph.device.HubAction("${start.join()}${String.format("%02X",light)}${end.join()}${String.format("%02X",checksum)}"))
}

// Configure the device
def configure() {
	def cmd=configurationCmds()
    // log.debug "Sending configuration: ${cmd}"
    return cmd
}

// Associate a load with the button, or clear the association if nodeid = 0
//
// nodeId:  a hex string, ie 4E for the Z-Wave node number
def associateLoad(String nodeId) {
	// log.debug "Associating nodeId: ${nodeId}" 
	def node = integerhex(nodeId)
    
	if (node != 0) {
    	updateState("associatedLoad", "1")
        updateState("associatedLoadId", nodeId)
		// log.debug "Node $nodeId associated with button 1"
    }
    else {
    	updateState("associatedLoad", "0")
        // log.debug "No nodes associated with button 1"
    }
   
   	configure()
}

// Associate the hub with the buttons on the device, so we will get status updates
def associateHub() {
    def commands = []
    def buttonlist = ['x', state.button1, state.button2, state.button3, state.button4]
    // log.debug "Entering associateHub() for ${device.displayName}..."
    // log.debug "state = ${state}"
    // log.debug "zwaveHubNodeId = ${[zwaveHubNodeId]}"
    // log.debug "buttonlist = ${buttonlist}"
    for (def buttonNum = 1; buttonNum <= integer(getDataByName("numButtons")); buttonNum++) {
		// log.debug "Loop for buttonNum = ${buttonNum}"
       	commands << zwave.sceneControllerConfV1.sceneControllerConfSet(groupId: buttonNum, sceneId: buttonNum, dimmingDuration: buttonNum).format()
        commands << zwave.sceneControllerConfV1.sceneControllerConfSet(groupId: buttonNum+4, sceneId: buttonNum+4, dimmingDuration: buttonNum+4).format()
    	// commands << zwave.remoteAssociationActivateV1.remoteAssociationActivate(groupingIdentifier:buttonNum).format()  //***** Parse error on cmd: 9100, payload: 1D 0C 0x 00
        if(buttonlist[buttonNum]) {
             // commands << zwave.associationV1.associationRemove(groupingIdentifier: buttonNum, nodeId: zwaveHubNodeId).format()
             commands << zwave.associationV2.associationSet(groupingIdentifier: buttonNum, nodeId: [zwaveHubNodeId] /* + integerhex([zwaveHubNodeId]) */).format()  //***** NumberFormatException on "001788280F07/5"
             commands << zwave.associationV2.associationSet(groupingIdentifier: buttonNum+4, nodeId: [zwaveHubNodeId] /* + integerhex([zwaveHubNodeId]) */).format()  //***** NumberFormatException on "001788280F07/5"
             // commands << zwave.associationV2.associationSet(groupingIdentifier: buttonNum, nodeId: integerhex(buttonlist[buttonNum]) ).format()  //***** NumberFormatException on "001788280F07/5" 
       	}                
		commands << zwave.sceneControllerConfV1.sceneControllerConfGet(groupId:buttonNum).format()
        commands << zwave.sceneControllerConfV1.sceneControllerConfGet(groupId:buttonNum+4).format()
    	commands << zwave.sceneActuatorConfV1.sceneActuatorConfGet(sceneId:buttonNum).format()
    	// commands << zwave.sceneActuatorConfV1.sceneActuatorConfSet(sceneId:buttonNum, level: 255, override: false).format()
    	// commands << zwave.sceneActuatorConfV1.sceneActuatorConfSet(sceneId:buttonNum, level: 32, dimmingDuration: 255, override: false).format()
    	commands << zwave.associationV2.associationGet(groupingIdentifier:buttonNum).format()
        commands << zwave.associationV2.associationGet(groupingIdentifier:buttonNum+4).format()
    	// commands << zwave.associationV2.associationReport(groupingIdentifier:buttonNum).format()
    	// commands << zwave.associationV2.associationSpecificGroupReport(group:buttonNum).format()
        // log.debug "commands: ${commands}"
	}    
    
    return commands
}

// Update State
// Store mode and settings
def updateState(String name, String value) {
	// log.debug "Updating ${name} to ${value}" 
	state[name] = value
	device.updateDataValue(name, value)
}

// Get Data By Name
// Given the name of a setting/attribute, lookup the setting's value
def getDataByName(String name) {
	state[name] ?: device.getDataValue(name)
}

/**
 * Builds a map of capability names to their supported commands.
 *
 * @param a list of Capabilities.
 * @return a map of device capability -> supported commands.
*/
def getDeviceCapabilityCommands(deviceCapabilities) {
	// log.debug "Getting device capability commands: ${deviceCapabilities}"
    def map = [:]
    deviceCapabilities.collect {
        map[it.name] = it.commands.collect{ it.name.toString() }
    }
    return map
}

//Stupid conversions

// convert a double to an integer
def integer(double v) {
	return v.toInteger()
}

// convert a hex string to integer
def integerhex(String v) {
	if (v == null) {
    	return 0
    }
    
	return v.split(',').collect { Integer.parseInt(it, 16) }
}

def integer(String v) {
	if (v == null) {
    	return 0
    }
    
	return Integer.parseInt(v)
}