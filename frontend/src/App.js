import React, {Component} from 'react'
import './App.css'
import axios from 'axios'
import MapComponent from './map-component'
import {AppBar, AutoComplete, Drawer, FlatButton, MenuItem} from 'material-ui'

class App extends Component {

    constructor() {
        super()
        this.state = {
            stations: [],
            stationsToShow: [],
            drawerOpen: false
        }
    }

    componentDidMount() {
        axios.get('http://localhost:8090/stations')
            .then(response => this.setState({stations: response.data, stationsToShow: response.data}))
    }

    toggleDrawer() {
        this.setState({drawerOpen: !this.state.drawerOpen})
    }

    handleClose = () => this.setState({drawerOpen: false})

    handleSelected = selected => {
        console.log('handleSelected')
        this.setState({
            stationsToShow: selected ?
                this.state.stations.filter(s => s.name === selected) : this.state.stations
        })
    }

    handleInputUpdated = searchText => {
        console.log('handleInputUpdated')
        if (!searchText) {
            this.setState({stationsToShow: this.state.stations})
        }
    }

    resetInput = () => {
        this.refs.autoComplete.setState({searchText: ''})
        this.handleInputUpdated('')
    }

    render() {
        return (
            <div className="App">
                <Drawer open={this.state.drawerOpen} docked={false}
                        onRequestChange={(drawerOpen) => this.setState({drawerOpen})}
                        disableSwipeToOpen={true}
                >
                    <MenuItem onClick={this.handleClose}>Menu Item</MenuItem>
                    <MenuItem onClick={this.handleClose}>Menu Item 2</MenuItem>
                </Drawer>
                <AppBar title="Vetunav"
                        onLeftIconButtonClick={() => this.toggleDrawer()}
                        iconElementRight={
                            <div>
                                <AutoComplete
                                    hintText="Type anything"
                                    dataSource={this.state.stations.map(s => s.name)}
                                    onNewRequest={this.handleSelected}
                                    onUpdateInput={this.handleInputUpdated}
                                    ref="autoComplete"
                                />
                                <FlatButton label="Clear" onClick={this.resetInput}/>
                            </div>
                        }
                ></AppBar>
                <MapComponent isMarkerShown
                              googleMapURL="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=geometry,drawing,places&key=AIzaSyBGAqshgprQLif3UO2Kz_CWB9ZN7HtHfaM"
                              loadingElement={<div style={{height: `100%`}}/>}
                              containerElement={<div style={{height: `600px`}}/>}
                              mapElement={<div style={{height: `100%`}}/>}
                              stations={this.state.stationsToShow}
                              activeStation={this.state.stationsToShow.length === 1 ?
                                  this.state.stationsToShow[0].name : ''}
                />
            </div>
        )
    }
}

export default App
