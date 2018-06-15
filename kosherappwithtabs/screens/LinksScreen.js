import React from 'react';
import { AppRegistry, SectionList, Image, ScrollView, StyleSheet, View, Text, Linking, Button, TouchableOpacity, Platform } from 'react-native';
import { Text, View, StyleSheet, ScrollView, Image } from 'react-native';
import { ExpoLinksView } from '@expo/samples';
import { Camera, Permissions } from 'expo';
import Panel from 'C:\Users\Shimon Cohen1\Documents\Publicgit\kosherappwithtabs\components\Panel.js';



export default class LinksScreen extends React.Component {
  static navigationOptions = {
    title: 'Approved Agencies',
    headerTintColor: "white",
    headerStyle: {
      backgroundColor: "#00A5FF",
    },
    headerTitleStyle: {
      color: "white",
      fontSize: 20,
    }
  };

  selectionList = () => {
    return([
      {data: [{title: "Kof-K"}, {title: "OU"}, {title: "Star K"}], key: "NY"},
      {data: [{title: 13}, {title: "OK"}], key: "NJ"},
    ])
  }

  render() {
    return (

    <ScrollView style = {styles.container}>
      <Card containerStyle={{padding: 0}} >
        {
          agencies.map((u, i) => {
            return (
              <ListItem
              key={i}
              roundAvatar style = {styles.picHeight}
              rightTitle={u.name}
              rightTitleStyle = {styles.rightTitle}
              subtitle = {u.phone}
              avatar={{uri:u.avatar}}
              />
            );
          })
        }
      </Card>
    </ScrollView>
        )
    }











/**
      <View style = {{marginTop : (Platform.OS) = 'ios' ? 20 : 0}}>
        <SectionList
        sections = {this.selectionList()}
          renderSectionHeader = { ({section}) =>
          <Text style = {{fontWeight: 'bold', fontSize : 24, textAlign: 'center'}}>{section.key}
          </Text>}
          renderItem = { ({item}) => <Text style = {{textAlign : 'center'}}> {item.title} </Text>}
          keyExtractor = {(item, index) => index}
          />
      </View>




      <View style={styles.welcomeContainer}>
        <Image
          source={
               require('../assets/images/cRcApproved.png')
          }
            style = {styles.welcomeImage}
        />
        </View>
          <View style = {styles.welcomeContainer}>
              <Image style = {styles.image}
                source ={
                  require('../assets/images/Kehilla-Kosher.png')
                }
              />
              <Text onPress={ ()=> Linking.openURL('http://www.koshermarks.com/business-directory/126/california-k/') }
              style={styles.getStartedText}>
              Igud Hakashrus of Los Angeles (Kehillah Kosher)
              </Text>
          </View>
          <Button buttonStyle = {styles.button1}
            title = "example"
            onPress={() => {}}/>
      </ScrollView>
    );
  }
  */
}


const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 15,
    backgroundColor: '#fff',
  },
  container1:{
    flex            : 1,
    backgroundColor : '#f4f7f9',
    paddingTop      : 30
  },
  image:{
      justifyContent: 'center',
      alignItems: 'center',
      flex: 1,
  },
  welcomeImage: {
    width: 100,
    height: 80,
    resizeMode: 'contain',
    marginTop: 3,
    marginLeft: -10,
  },
  welcomeContainer: {
    alignItems: 'center',
    marginTop: 10,
    marginBottom: 20,
    borderRadius: 4,
    borderWidth: 1,
    borderColor: '#707b7c',
  },
  symbolContainer: {
  alignItems: 'center',
  },
  button: {
    alignItems: 'center',
    borderColor: '#ADD8E6',
    padding: 10,
    borderRadius: 4,
    borderWidth: 1,
  },
  alternativeLayoutButtonContainer: {
    borderRadius: 4,
    borderWidth: 1,
    borderColor: '#ADD8E6',
    padding: 20,
    flexDirection: 'column',
    backgroundColor: '#F5F5F5',
    width: Dimensions.get('window').width,
  },
  getStartedText: {
    fontSize: 17,
    color: 'rgba(96,100,109, 1)',
    textAlign: 'center',
  },
  tabBarInfoContainer: {
    position: 'absolute',
    bottom: 0,
    left: 0,
    right: 0,
    alignItems: 'center',
    backgroundColor: '#fbfbfb',
    paddingVertical: 20,
  },
  button1: {
      backgroundColor: '#00aeef',
      borderColor: 'red',
      borderWidth: 5,
      borderRadius: 15
   },
sectionHeader: {
  paddingTop: 2,
  paddingLeft: 10,
  paddingRight: 10,
  paddingBottom: 2,
  fontSize: 14,
  fontWeight: 'bold',
  backgroundColor: 'rgba(247,247,247,1.0)',
},
item: {
  padding: 10,
  fontSize: 18,
  height: 44,
},
});
