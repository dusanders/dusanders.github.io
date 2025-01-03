import React from "react";
import { Strings } from "../../context/locale/Locale";

export interface HeadingProps {
  locale: Strings
}

export function Heading(props: HeadingProps) {
  return (
    <div className="heading-div">
      <h1 className="heading-text heading-firstName">
        {props.locale.myFirstName}
      </h1>
      <h1 className="heading-text heading-lastName">
        {props.locale.myLastName}
      </h1>
      <span className="desc-text">
        {props.locale.myJobTitle}
      </span>
    </div>
  )
}