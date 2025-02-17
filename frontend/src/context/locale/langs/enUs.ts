import { Strings } from "../Locale";

export class Strings_enUS implements Strings {
  myFirstName: string = "Dustin";
  myLastName: string = "Anderson";
  myJobTitle: string = "Senior Software Engineer";
  about: string = 'About';
  work: string = 'Work';
  passion: string = 'Passion';
  education: string = 'Education';
  chooser = {
    enUs: 'English (US)',
    knightsOfNi: 'Knights of Ni'
  };
  aboutSection = {
    title: 'About Me',
    desc: `Full-stack software engineer with over 8 years of experience in a fast-paced
    startup company. I started as a junior developer and worked into a 
    team lead position where I was managing projects and team members.`
  };
}