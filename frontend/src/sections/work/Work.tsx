import React from "react";
import './Work.scss';
import { Container, Grid2, Link, List, ListItem, Paper, Typography } from "@mui/material";
import { SectionTitle } from "../../components/sectionTitle/SectionTitle";
import { ILocaleContext } from "../../context/locale/Locale";
import { ProductCard, ProductCardProps } from "./projectCard";
import { Utils } from "../../types/types";
import { Masonry } from "@mui/lab";
import { BackgroundImage } from "../../components/backgroundImage/backgroundImage";
import { StaticAssets } from "../../assets";
import { CardTitle } from "./cardTitle";

export interface WorkProps {
  locale: ILocaleContext
}

function WorkSummaryText(props: { children?: any }) {
  return (
    <Typography className="work-summary">
      {props.children}
    </Typography>
  )
}
function ProductSummary(props: { children?: any, className?: string }) {
  return (
    <Typography className={`summary ${props.className || ''}`}
      gutterBottom>
      {props.children}
    </Typography>
  )
}
const products: ProductCardProps[] = [
  {
    productName: 'LINK',
    href: 'https://flatt.design/fasetto-link',
    productSummary: (
      <ProductSummary className="product">
        An SSD with a WiFi card - providing a personal Cloud in your pocket!
      </ProductSummary>
    ),
    myWorkSummary: (
      <>
        <ProductSummary>
          I was tasked with designing and implementing Linux software to control various hardware
          operations from a web application.
        </ProductSummary>
        <ProductSummary>
          I also implemented Web UI according to UX designs with an in-house JS / CSS framework to
          provide browser access to hardware features.
        </ProductSummary>
        <ProductSummary>
          Assisted in the development of an Android application to interact with the hardware. Sending
          and receiving data to the embedded server.
        </ProductSummary>
      </>
    ),
    skills: [
      {
        skillName: 'Embedded Linux',
        skillPoints: [
          'Build custom Linux distros and BSP packages for SoC board solution.',
          'Customized Linux drivers and Bash scripts to control WiFi on SoC.',
          'Evaluate, test, and report on various SSD solutions for SoC board.'
        ]
      },
      {
        skillName: 'Full-stack .NET Core',
        skillPoints: [
          '.NET Core server code on custom Linux; running on SoC.',
          'Support files for bridging functionality from .NET Core to base Linux OS for performing kernel-level functionality from Web UI.',
          'In-house JS / CSS framework for web UI accessible from connected devices.'
        ]
      },
      {
        skillName: 'Xamarin (Android)',
        skillPoints: [
          'Develop Android-side of Xamarin app for interacting with LINK via BT / BLE',
          'Incorporate Web UI functionality into Android app for interacting with LINK via WiFi'
        ]
      }
    ],
  },
  {
    productName: 'Gravity',
    href: 'https://www.fasetto.com/gravity',
    productSummary: (
      <ProductSummary className="product">
        Device marshalling software that would allow a central device to control the content on any number of other devices.
      </ProductSummary>
    ),
    myWorkSummary: (
      <>
        <ProductSummary>
          Gravity was a larger project under Fasetto, where my team was assigned the 'streaming' aspect of the framework. The basic product
          design was as follows:
        </ProductSummary>
        <List sx={{ fontWeight: 600, fontSize: '0.7rem', width: '80%', margin: 'auto' }}>
          <ListItem>
            <ProductSummary>
              Design and implement a mobile application that would turn an Android device into a central controller for n-devices
              on the same WiFi network.
            </ProductSummary>
          </ListItem>
          <ListItem>
            <ProductSummary>
              Design, compile, and implement a Linux solution for an embedded SoC hardware device that would display media on
              a screen via HDMI.
            </ProductSummary>
          </ListItem>
          <ListItem>
            <ProductSummary>
              Design and implement an Android application that would be responsible for taking commands from the central mobile
              device and control the media displayed on the tablet. Basically, turn a tablet in a remote controlled kiosk.
            </ProductSummary>
          </ListItem>
        </List>
        <ProductSummary>
          This solution was displayed at CES, where I was also tasked with complete booth setup, maintainence, and interacting with
          show attendees during show hours.
        </ProductSummary>
      </>
    ),
    skills: [
      {
        skillName: 'Embedded Linux',
        skillPoints: [
          'Design, evaluate, and implement Linux distros to provide an OS for a small hardware device that would be \
          connected to a screen via HDMI.',
          'Design and implement Linux functionality to produce a remote controlled \'media kiosk\' UI for the embedded device.'
        ]
      },
      {
        skillName: 'Node',
        skillPoints: [
          'Design, implement, and deploy Node servers on embedded Linux that would send / recieve data via WiFi from a mobile device',
          'Design, implement, and deploy Node servers from an Android device. The Android device was required to be a central server \
          that controls other connected devices.',
          'Node server was required to serve Web UI along with REST api for media files and control commands.'
        ]
      },
      {
        skillName: 'React',
        skillPoints: [
          'Design and implement a web UI for a remove media kiosk based on mock-ups from the graphics team.',
          'React app required mock app selection, media display, and control commands; instructing other devices to display a specific \
          media file. In addition, the app would provide functionality for monitoring and controlling the playback position of video media.'
        ]
      },
      {
        skillName: 'Cordova',
        skillPoints: [
          'Design and implement an Android Cordova application that would implement a React UI.',
          'Cordova app was required to host a \'publically\' available Node server and Web UI to other devices via WiFi.'
        ]
      }
    ]
  },
  {
    productName: 'Flysview',
    href: 'https://www.fasetto.com/flysview',
    productSummary: (
      <ProductSummary className="product">
        Flysview is a multi-device, video streaming solution.
        The software allows for multiple devices to stream video content between
        them to accomplish a multi-angle video recording. Video content can be
        saved and watched later while preserving the synchronization and allowing
        the viewer to select whichever angle they want in a custom video player.
      </ProductSummary>
    ),
    myWorkSummary: (
      <>
        <ProductSummary>
          My team was tasked with designing and implementing an a web server that would
          be capable of ingesting and forwarding multiple live vide streams to multiple devices.
        </ProductSummary>
        <ProductSummary>
          My team was tasked with implementing a UI/UX for web and mobile apps designed by
          the graphics teams.
        </ProductSummary>
        <ProductSummary>
          Create, maintain, and coordinate mobile app releases between iOS and Android across
          Play Store, Apple Store, along with TestFlight and Android Test Tracks.
        </ProductSummary>
      </>
    ),
    skills: [
      {
        skillName: 'Node',
        skillPoints: [
          'Design and implement a destributed micro-service architecture that allows \
          for processing multiple real-time video streams across different devices while relaying \
          that video content for viewers.',
          'Design and implement a Web UI for browser access that would allow anyone to view the \
          real-time video streams.',
          'Design and implement a mobile application for iOS and Android that would stream live video \
          to a server and recieve live video from other connected devices.'
        ]
      },
      {
        skillName: 'React',
        skillPoints: [
          'Design and implement the web UI for browser access to view and manage video content from multiple \
          live video streams.',
          'Design and implement customized video player that controlled playback for synchronized video streams \
          across multiple devices.'
        ]
      },
      {
        skillName: 'React Native',
        skillPoints: [
          'Design and implement a mobile application for iOS and Android that would stream live video to a server and \
          other connected devices to accomplish the multi-angle goal of the product.',
          'Utilize network technologies to handshake, coordinate, and stream video content between multiple iOS and Android devices.'
        ]
      },
      {
        skillName: 'Play Store and Apple App Store',
        skillPoints: [
          'Setup, maintain, and coordinate app releases on iOS and Android app stores. Ensure store listings followed UI guidelines \
          provided by UI/UX team.',
          'Coordinate test releases across iOS and Android utilizing TestFlight and Google\'s test track for targeted testing \
          deploys before rolling over to production.'
        ]
      }
    ]
  },
  {
    productName: 'Audio Cu',
    href: 'https://www.fasetto.com/audiocu',
    productSummary: (
      <ProductSummary className="product">
        AUDIO Cu is a Fasetto product that carries digital audio data over power lines. The product consists of a transceiver sending data out and multiple
        receivers receiving the signal and powering speakers.
      </ProductSummary>
    ),
    myWorkSummary: (
      <>
        <ProductSummary>
          I was tasked with implementing the AUDIO Cu mobile application for controlling the hardware devices.
        </ProductSummary>
      </>
    ),
    skills: [
      {
        skillName: 'React Native',
        skillPoints: [
          'Design and implement an iOS and Android application according to UI/UX designed by a team of graphic designers',
          'Implement functionality that would communicate with the AUDIO Cu transceiver to send and receive commands.',
          'Implement functionality to perform drag / drop speaker configurations, EQ settings per speaker, and many more \
          innovative UX requirements.'
        ]
      },
      {
        skillName: 'Play Store and Apple App Store',
        skillPoints: [
          'Setup, maintain, and coordinate app releases on iOS and Android app stores. Ensure store listings followed UI guidelines \
          provided by UI/UX team.',
          'Coordinate test releases across iOS and Android utilizing TestFlight and Google\'s test track for targeted testing \
          deploys before rolling over to production.'
        ]
      }
    ]
  }
]

export function WorkSection(props: WorkProps) {
  return (
    <section id="work" className="work-root">
      <Paper elevation={8} className="work-paper">
        <Container className="background-content">
          <SectionTitle>
            {props.locale.strings.work}
          </SectionTitle>
          <WorkSummaryText>
            Full-stack software engineer with over 8 years at Fasetto, a faced-paced startup.
          </WorkSummaryText>
          <WorkSummaryText >
            I started as a junior developer and eventually worked into
            team lead where I was managing a small team of developers to design, implement, and deploy innovative products.
          </WorkSummaryText>
          <WorkSummaryText>
            During my time at Fasetto, I worked in numerous different
            technology stacks:
          </WorkSummaryText>
          <div className="work-summary skills-list-h6">
            <span>
              Linux in embedded devices, .NET Core, Node.js, Next.js, ReactJS, React Native, SQLite, MongoDB, WebRTC,
              and many other technologies.
            </span>
          </div>
          <Masonry
            className="masonry-root"
            columns={{ md: 2, xl: 3 }}>
            {products.map((product) => (
              <Grid2 size={{ md: 2, lg: 5 }} key={Utils.generateGuid()}
                component={'div'}>
                <ProductCard
                  productName={product.productName}
                  myWorkSummary={product.myWorkSummary}
                  href={product.href}
                  productSummary={product.productSummary}
                  skills={product.skills} />
              </Grid2>
            ))}
          </Masonry>
        </Container>
      </Paper>
    </section>
  )
}