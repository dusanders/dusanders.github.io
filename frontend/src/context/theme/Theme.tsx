import { createTheme, CssBaseline, ThemeProvider } from "@mui/material";
import React from "react";

export interface ThemedProps {
  children?: any;
}

const defaultTheme = createTheme({

})
export function Themed(props: ThemedProps) {
  return (
    <ThemeProvider theme={defaultTheme}>
      <CssBaseline />
      {props.children}
    </ThemeProvider>
  )
}