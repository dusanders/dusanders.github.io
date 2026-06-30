import React from "react";
import "./Navbar.scss";
import { Typography } from "../typography/Typography";

export function Navbar() {
  return (
    <div className="nav-root">
      <Typography variant="bold" className="nav-name">
        {`>_ Dustin_Anderson `}
      </Typography>
      <Typography variant="faded">
        {` // Senior_Software_Engineer `}
      </Typography>
      <Typography variant="faded">
        {` // Mobile_IoT_Web `}
      </Typography>
    </div>
  )
}