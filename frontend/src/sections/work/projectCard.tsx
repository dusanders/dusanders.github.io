import { Accordion, Card, CardContent, Link, List, ListItem, Typography } from "@mui/material";
import React from "react";
import { CardTitle } from "./cardTitle";
import { AccordianItem } from "./accordianItem";
import { Utils } from "../../types/types";


export interface ProductSkill {
  skillName: string;
  skillPoints: string[];
}

export interface ProductCardProps {
  productName: string;
  href?: string;
  productSummary: JSX.Element;
  myWorkSummary: JSX.Element;
  skills: ProductSkill[];
}

export function ProductCard(props: ProductCardProps) {
  return (
    <Card variant={'elevation'}
      className="card">
      <CardContent className="content">
        <CardTitle
          href={props.href || ''}
          title={props.productName}
          summary={props.productSummary} />
        {props.myWorkSummary}
        {props.skills.map((skill) => (
          <div
            key={Utils.generateGuid()}>
            <AccordianItem
              title={skill.skillName}>
              <List>
                {skill.skillPoints.map((point) => (
                  <ListItem
                    key={Utils.generateGuid()}
                    className="skill-list-item">
                    {point}
                  </ListItem>
                ))}
              </List>
            </AccordianItem>
          </div>
        ))}
      </CardContent>
    </Card>
  )
}