import { ExpandCircleDown } from "@mui/icons-material";
import { Accordion, AccordionDetails, AccordionSummary, Typography } from "@mui/material";
import React from "react";
import './Work.scss';

export interface AccordianItemProps {
  title: string;
  children?: any;
}

export function AccordianItem(props: AccordianItemProps) {
  return (
    <Accordion
      className="accordian-base">
      <AccordionSummary
        className="accordian-summary"
        expandIcon={<ExpandCircleDown />}>
        <div className="column">
          <div className="accordian-item">
            <Typography>
              {props.title}
            </Typography>
          </div>
        </div>
      </AccordionSummary>
      <AccordionDetails
        sx={{ borderRadius: '1rem' }}>
        {props.children}
      </AccordionDetails>
    </Accordion>
  )
}