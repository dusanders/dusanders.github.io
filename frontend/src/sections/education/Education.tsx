import React from "react";
import './Education.scss';
import { SectionTitle } from "../../components/sectionTitle/SectionTitle";
import { Container, Divider, Typography } from "@mui/material";
import { Utils } from "../../types/types";
import { Masonry } from "@mui/lab";

export interface EducationProps {

}

interface SchoolInfoProps {
  name: string;
  sublines: string[];
}

function SchoolInfo(props: SchoolInfoProps) {
  return (
    <Container maxWidth={'md'}
      className="school-container">
      <Typography className="schoolname-text">
        {props.name}
      </Typography>
      {props.sublines.map((line) => (
        <Typography key={Utils.generateGuid()}
          className="school-subline">
          {line}
        </Typography>
      ))}
      <Divider />
    </Container>
  )
}
export function EducationSection(props: EducationProps) {
  return (
    <section id="education" className="education-root">
      <Container maxWidth={'md'}>
        <SectionTitle>
          Education
        </SectionTitle>
        <Container maxWidth={'md'}>
          <Masonry columns={{md: 1, lg: 3}}>
            <SchoolInfo
              name="University of Wisconsin-Superior"
              sublines={[
                'BSc. in Computer Science',
                'Minoring in Information Technology'
              ]} />
            <SchoolInfo
              name="Lake Superior College"
              sublines={[
                'Associate of Science'
              ]} />
            <SchoolInfo
              name="Lake Superior College"
              sublines={[
                'Associate of Applied Science in Automotive Technology'
              ]} />
          </Masonry>
        </Container>
      </Container>
    </section>
  )
}