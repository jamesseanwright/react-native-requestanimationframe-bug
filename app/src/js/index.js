import React from 'react';

import {
    AppRegistry,
    StyleSheet,
    View,
    Text
} from 'react-native';


const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
    },
})

class App extends React.Component {
    constructor() {
        super();

        this.state = {
            time: 0,
        };

        requestAnimationFrame(time => (
            this.setState({
                time,
            })
        ));
    }

    render() {
        const { showProgressBar } = this.state;

        return (
            <View style={styles.container}>
                <Text>{this.state.time}</Text>
            </View>
        );
    }
}

AppRegistry.registerComponent('App', () => App);
