import React from "react";
import { Strings } from "../../context/locale/Locale";
import { Container, Link, Typography } from "@mui/material";
import { LinkConstants } from "../../types/types";
import { Email, GitHub, LinkedIn } from "@mui/icons-material";

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
      <Container maxWidth={'xs'}
        className="link-div">
        <Link
          href={LinkConstants.LinkedInUrl}
          rel={'noopener'}
          target={'_blank'}>
          <LinkedIn />
        </Link>
        <Link
          href={LinkConstants.MyGithubUrl}
          rel={'noopener'}
          target={'_blank'}>
          <GitHub />
        </Link>
        <Link
          href={'mailto:dusanders@gmail.com'}>
          <Email />
        </Link>
      </Container>
    </div>
  )
}