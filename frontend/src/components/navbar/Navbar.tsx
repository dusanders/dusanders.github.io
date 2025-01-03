import React, { useRef, useState } from "react";
import "./Navbar.scss";
import { AppBar, Button, Link, Toolbar } from "@mui/material";
import { Sections, useScrollListener } from "../../types/types";
import { ILocaleContext } from "../../context/locale/Locale";
import { RightSide } from "./rightSide";
import { NavButton } from "./navButton";

export interface NavbarProps {
  locale: ILocaleContext;
}

export function Navbar(props: NavbarProps) {
  const strings = props.locale.strings;
  const [scrollAtTop, setScrollAtTop] = useState(true);
  const localeWrapperRef = useRef<HTMLDivElement>(null);

  useScrollListener((scrollY) => {
    if (scrollY <= 300) {
      setScrollAtTop(true);
    } else {
      setScrollAtTop(false);
    }
  });

  const getAppBarScrollClassname = () => {
    return scrollAtTop
      ? ''
      : 'nav-AppBar-scrolled';
  }

  return (
    <div className="nav-root" ref={localeWrapperRef}>
      <AppBar
        className={`nav-AppBar ${getAppBarScrollClassname()}`}
        enableColorOnDark
        elevation={scrollAtTop ? 0 : 4}>
        <Toolbar className="nav-Toolbar">
          {!scrollAtTop && (
            <NavButton href={`${Sections.Intro}`}
              className="nav-intro_link">
              {strings.myFirstName} {strings.myLastName}
            </NavButton>
          )}
          <RightSide
            locale={props.locale} />
        </Toolbar>
      </AppBar>
    </div>
  )
}