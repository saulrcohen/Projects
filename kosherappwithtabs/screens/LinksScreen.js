import React from 'react';
import { AppRegistry, Image, ScrollView, StyleSheet, View, Text, Linking Platform } from 'react-native';
import { Card, ListItem } from 'react-native-elements';



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

  render() {
      const agencies = [
          {
          name: "Orthodox Union",
          city: "Teaneck",
          avatar: 'https://oukosher.org/content/uploads/2012/11/OU-Logo.jpg',
          phone: "(212) 563-4000"
          },
          {
            name: "Kof-K",
            city: "Teaneck",
            avatar: 'http://s3.amazonaws.com/shmoogle/wp-content/uploads/kof-k.gif',
            phone: "(201) 837-0500"
          },
          {
            name: "OK",
            city: "Teaneck",
            avatar: 'https://www.jewishlinknj.com/images/news/2015/93_aug06/44.jpg',
            phone: "(718) 756-7500"
          },
          {
            name: "Khal Adath Jeshurun",
            city: "Teaneck",
            avatar: 'http://www.crcweb.org/images/images_agencies/0141~KAJ.jpg',
            phone: "845-425-9089"
          },
          {
            name: "Igud Hakashrus of Los Angeles",
            city: "Los Angeles",
            avatar: 'http://www.fairdinks.com.au/product_images/uploaded_images/kosher-symbol-usa08-kehilla-kosher.jpg',
            phone : "(323) 935-8383"
          },
          {
            name: "Vaad Hakashrus of Northern California",
            city: "Los Angeles",
            avatar: 'https://static.wixstatic.com/media/4676c8_3687292b22c447b0b07f190dcbb76721~mv2.jpg/v1/fill/w_243,h_143,al_c,q_80/4676c8_3687292b22c447b0b07f190dcbb76721~mv2.webp',
            phone : "(510) 843-8223"
          },
          {
            name: "RCC",
            city: "Los Angeles",
            avatar: 'http://www.koshermarks.com/wp-content/uploads/2013/06/rcc_logo.jpg',
            phone : "213-389-3382"
          },
          {
            name: "Rabbinical council of Orange County and Long Beach",
            city: "Los Angeles",
            avatar: 'http://www.vaad.org/wp-content/uploads/2013/02/Rabbinical-Council-of-Orange-County-Long-Beach.jpg',
            phone : "562- 426-4894"
          },
          {
            name: "Kosher Supervision of America ",
            city: "Los Angeles",
            avatar: 'https://i.vimeocdn.com/portrait/5679067_300x300',
            phone : "310-282-0444"
          }
        ];


    return (
     <ScrollView style = {styles.container}>
       <Card containerStyle={{padding: 5}}
       title = "New York"
       titleStyle = {styles.titleStyles}>
         {
           agencies.map((u, i) => {
             return (
               <ListItem
               key={i}
               title = {u.name}
               titleStyle = {styles.rightTitle}
               subtitle = {u.phone}
               leftSubtitle = {u.city}
               avatar={{uri:u.avatar}}
               />
             );
           })
         }
       </Card>
     </ScrollView>
         )
     }
   }
}


const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 20,
    paddingBottom: 60,
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
  titleStyles:{
    fontSize: 28,
    color: '#0C36C8',
    alignItems: 'center',
  },
  rightTitle:{
    fontWeight: 'bold',
    fontSize: 20,
  },
});
