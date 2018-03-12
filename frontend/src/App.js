import React, {Component} from 'react'
import './App.css'
import axios from 'axios'
import MapComponent from './map-component'

class App extends Component {

    constructor() {
        super()
        this.state = {
            stations: []
        }
    }

    componentDidMount() {
        axios.get('http://localhost:8090/stations')
            .then(response => this.setState({stations: response.data}))
    }

    render() {
        return (
            <div className="App">
                <MapComponent isMarkerShown
                              googleMapURL="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=geometry,drawing,places&key=AIzaSyBGAqshgprQLif3UO2Kz_CWB9ZN7HtHfaM"
                              loadingElement={<div style={{height: `100%`}}/>}
                              containerElement={<div style={{height: `600px`}}/>}
                              mapElement={<div style={{height: `100%`}}/>}
                              stations={this.state.stations}
                />
            </div>
        )
    }
}

export default App
