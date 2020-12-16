import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'interviewee',
        loadChildren: () => import('./interviewee/interviewee.module').then(m => m.InterviewdbIntervieweeModule),
      },
      {
        path: 'question',
        loadChildren: () => import('./question/question.module').then(m => m.InterviewdbQuestionModule),
      },
      {
        path: 'interview-question',
        loadChildren: () => import('./interview-question/interview-question.module').then(m => m.InterviewdbInterviewQuestionModule),
      },
      {
        path: 'q-tag',
        loadChildren: () => import('./q-tag/q-tag.module').then(m => m.InterviewdbQTagModule),
      },
      {
        path: 'question-tag',
        loadChildren: () => import('./question-tag/question-tag.module').then(m => m.InterviewdbQuestionTagModule),
      },
      {
        path: 'question-set',
        loadChildren: () => import('./question-set/question-set.module').then(m => m.InterviewdbQuestionSetModule),
      },
      {
        path: 'question-set-tag',
        loadChildren: () => import('./question-set-tag/question-set-tag.module').then(m => m.InterviewdbQuestionSetTagModule),
      },
      {
        path: 'interview-question-set',
        loadChildren: () =>
          import('./interview-question-set/interview-question-set.module').then(m => m.InterviewdbInterviewQuestionSetModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class InterviewdbEntityModule {}
