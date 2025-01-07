import React from "react";
import './About.scss';
import { Divider, Link, Paper, Typography } from "@mui/material";
import { ILocaleContext, SupportedLocales } from "../../context/locale/Locale";
import { SectionTitle } from "../../components/sectionTitle/SectionTitle";
import { Sections } from "../../types/types";
import { StaticAssets } from "../../assets";
import { BackgroundImage } from "../../components/backgroundImage/backgroundImage";

export interface AboutProps {
  locale: ILocaleContext;
}

function AboutTypography(props: { children: any }) {
  return (
    <Typography className="about-typography">
      {props.children}
    </Typography>
  )
}

/**
 * Helper function to place a Link element into a Typography string
 * @param fullString Full string to display
 * @param toReplace Token string to replace
 * @param href href to use in Link element
 * @returns Typography element with inserted Link element
 */
function InsertLink(fullString: string, toReplace: string, href: string) {
  const replaceIndex = fullString.indexOf(toReplace);
  return (
    <>
      <AboutTypography>
        {fullString.substring(0, replaceIndex)}
        <Link href={`${href}`}
          target={'_blank'}
          rel={'noopener'}>
          {toReplace}
        </Link>
        {fullString.substring(replaceIndex + toReplace.length)}
      </AboutTypography>
    </>
  );
}

export function AboutSection(props: AboutProps) {

  return (
    <section id="about"
      className="about-root">
      <BackgroundImage
        className="background-smart-position"
        url={StaticAssets.MeAtGrandCanyon}
        smokeScreen
        contentClassName="background-content">
        <Paper className="paper-root"
          elevation={15}>
          <SectionTitle>
            {props.locale.strings.aboutSection.title}
          </SectionTitle>
          <div className="content">
            {props.locale.locale !== SupportedLocales.en_US && (
              <AboutTypography>
                {props.locale.strings.aboutSection.desc}
              </AboutTypography>
            )}
            {props.locale.locale === SupportedLocales.en_US && (
              <>
                <AboutTypography>
                  BSc. in Computer Science, minoring in Information Technology from the University of Wisconsin-Superior.
                </AboutTypography>
                <AboutTypography>
                  I have experience with various non-technical assignments such as; working CES show booths where I
                  would be required to ensure booth functionality and demonstrate Fasetto products to the general public.
                  Additionally, I was required to attended numerous investor meetings, travel obligations overseas, and working
                  with various teams across time zones.
                </AboutTypography>
                <div>
                  <Typography fontWeight={600} fontSize={'1.12rem'}>Hobbies</Typography>
                  <Divider sx={{ margin: '0.4rem 0rem' }} />
                  <AboutTypography>
                    When I am not creating software; I enjoy the outdoors, working on cars, sim racing, and bowling.
                  </AboutTypography>
                  <AboutTypography>
                    I love spending time at my cabin, with acres of trails and tons of wildlife to watch while high speed Starlink 
                    internet also helps to stay connected when needed.
                  </AboutTypography>
                  <AboutTypography>
                    I used to work as a mechanic; now that I can enjoy it as a hobby
                    again, I like to work on my Camaro z28 and my wife's Miata.
                  </AboutTypography>
                  <AboutTypography>
                    I have always been a fan of racing games and recently setup a new simulator utilizing Fanatec's
                    CSL DD and Elite pedals. Enjoying Assetto Corsa, Assetto Corsa Competizione, Forza Motorsports,
                    and the Horizon series.
                  </AboutTypography>
                  <AboutTypography>
                    I have been an avid bowler for about 5 years, having been part of a casual league with my wife
                    for the last 4 years.
                  </AboutTypography>
                </div>
              </>
            )}
          </div>
        </Paper>
      </BackgroundImage>
    </section>
  )
}