import { Check } from "@mui/icons-material";
import React from "react";
import { SupportedLocales } from "../../context/locale/Locale";
import { Typography } from "@mui/material";

export interface LocaleItemProps {
  onClick(): void;
  isSelected: boolean;
  children?: any;
}

export function LocaleItem(props: LocaleItemProps) {
  return (
    <div className="item-root"
      onClick={() => {
        props.onClick()
      }}>
      {props.isSelected && (
        <div className="icon">
          <Check style={{height: '1rem'}}/>
        </div>
      )}
      {!props.isSelected && (
        <div className="icon"></div>
      )}
      <Typography>
        {props.children}
      </Typography>
    </div>
  )
}