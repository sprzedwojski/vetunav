import React from 'react'
import {withScriptjs, withGoogleMap, GoogleMap, Marker, InfoWindow} from 'react-google-maps'

class MapComponent extends React.Component {

    constructor() {
        super()
        this.state = {
            activeStation: ''
        }
    }

    componentWillReceiveProps(nextProps) {
        this.setState({activeStation: nextProps.activeStation})
    }
    
    toggleOpen(stationName) {
        if(stationName === this.state.activeStation) {
            this.setState({activeStation: ''})
        } else {
            this.setState({activeStation: stationName})
        }
    }

    render() {
        return (
            <GoogleMap
                defaultZoom={11}
                defaultCenter={{lat: 52.237049, lng: 21.017532}}
            >
                {this.props.stations.map(station =>
                    <Marker key={station.name} position={{lat: station.latitude, lng: station.longitude}}
                            onClick={() => this.toggleOpen(station.name)}>
                        {station.name === this.state.activeStation &&
                        <InfoWindow onCloseClick={() => this.toggleOpen(station.name)}>
                            <div>
                                <div>{station.name}</div>
                                <div>Wolne rowery: {station.bikes}</div>
                                <div>Wolne miejsca: {station.freeRacks}</div>
                            </div>
                        </InfoWindow>}
                    </Marker>
                )}
            </GoogleMap>
        )
    }
}

export default withScriptjs(withGoogleMap(MapComponent))