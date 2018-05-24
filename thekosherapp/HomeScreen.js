class HomeScreen extends React.Component{
  static navigationOptions = {
    title : 'Welcome',
  };
  render() {
    const { navigate } = this.props.navigation;
    return (
      //<Button
      //title = 'Shalom Aleichem'
      //onPress = {() =>
      //null
        //}
      ///>
      <Text>Home Screen </Text>
    );
  }
}
