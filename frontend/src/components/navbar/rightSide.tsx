import { Language } from "@mui/icons-material";
import { Button, Link } from "@mui/material";
import React, { useRef, useState } from "react";
import { Sections, useOutsideOnClick, useScrollListener } from "../../types/types";
import { ILocaleContext } from "../../context/locale/Locale";
import { LocaleFlyout } from "./localeFlyout";
import './Navbar.scss';
import { NavButton } from "./navButton";

export interface RightSideProps {
  locale: ILocaleContext;
}
function isScrollAtTop(scrollY: number) {
  return scrollY > 300;
}
export function RightSide(props: RightSideProps) {
  const strings = props.locale.strings;
  const chooserWrapperRef = useRef<HTMLDivElement>(null);
  const [showLocale, setShowLocale] = useState(false);
  const [isAtTop, setIsAtTop] = useState(isScrollAtTop(window.scrollY));

  useOutsideOnClick(chooserWrapperRef, () => {
    setShowLocale(false);
  });

  useScrollListener((scrollY) => {
    setShowLocale(false);
    setIsAtTop(isScrollAtTop(scrollY));
  });

  return (
    <div className="rightSide">
      <NavButton href={`${Sections.Work}`}>
        {strings.work}
      </NavButton>
      <NavButton href={`${Sections.About}`}>
        {strings.about}
      </NavButton>
      <NavButton href={`${Sections.Education}`}>
        {strings.education}
      </NavButton>
      <NavButton href={`${Sections.Passion}`}>
        {strings.passion}
      </NavButton>
      {!isAtTop && (
        <div ref={chooserWrapperRef}
          className="localeChooserWrapper">
          <Button className="nav-btn"
            onClick={() => {
              setShowLocale(!showLocale);
            }}>
            <Language className="locale-icon" />
          </Button>
          {showLocale && (
            <LocaleFlyout
              locale={props.locale} />
          )}
        </div>
      )}
    </div>
  )
}