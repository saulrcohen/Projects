import React,{Component,StyleSheet,Text,View,Image,TouchableHighlight,Animated} from 'react-native';


class panel extends React.Component{
  constructor(props){
    super(props);


    this.icons ={
      'up' : require('./images/Arrow_up.png'),
      'down' : require('./images/Arrow_down.png')
    };

    this.state = {
      tite : props.title,
      expanded : true
    };
  }

  toggle(){

  }

  render(){
    let icon = this.icons['down'];
    if(this.state.expanded){
      icon = this.icons['up'];
    }

    return(
      <View style = styles.container}>
        <View style = {styles.titleContainer}>
          <Text style = {styles.title}>{this.state.title}</Text>
            <TouchableHighlight
            style = {styles.button}
            onPress = {this.toggle.bind{this}}
            underLayColor = '#f1f1f1'>
            image
            style = {styles.buttonImage}
            source = {icon}
            ></Image>
          </TouchableHighlight
        </View>

        <View style = {styles.body}>
          {this.props.children}
        </View>

    </View>
    );
  }
}

export default Panel;


var styles = StyleSheet.create({
  container   : {
      backgroundColor: '#fff',
      margin:10,
      overflow:'hidden'
  },
  titleContainer : {
      flexDirection: 'row'
  },
  title       : {
      flex    : 1,
      padding : 10,
      color   :'#2a2f43',
      fontWeight:'bold'
  },
  button      : {

  },
  buttonImage : {
      width   : 30,
      height  : 25
  },
  body        : {
      padding     : 10,
      paddingTop  : 0
  }
});
