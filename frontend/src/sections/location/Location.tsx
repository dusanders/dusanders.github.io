import React from "react";
import "./Location.scss";
import { Typography } from "../../components/typography/Typography";
import { Card } from "../../components/card/Card";

export function Location() {
  return (
    <Card 
    border={"none"}
    className="location" 
    title="Location">
      <div className="location__content">
        <div className="location__content__text">
          <Typography variant="faded">
            {'Minnesota, USA'}
          </Typography>
          <Typography variant="faded">
            {'Central Time Zone (GMT-6)'}
          </Typography>
        </div>
      </div>
    </Card>
  );
}