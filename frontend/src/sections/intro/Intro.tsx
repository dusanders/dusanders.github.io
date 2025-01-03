import React from "react";
import "./Intro.scss";
import { Heading } from "./heading";
import { BackgroundImage } from "../../components/backgroundImage/backgroundImage";
import { ILocaleContext } from "../../context/locale/Locale";
import { StaticAssets } from "../../assets";

export interface IntroProps {
  locale: ILocaleContext;
  children?: any;
}

export function IntroSection(props: IntroProps) {
  return (
    <section id="intro" className="intro-root">
      <BackgroundImage
      url={StaticAssets.MeOnPier}>
      <Heading locale={props.locale.strings}/>
      </BackgroundImage>
    </section>
  )
}