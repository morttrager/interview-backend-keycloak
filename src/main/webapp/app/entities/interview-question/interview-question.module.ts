import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InterviewdbSharedModule } from 'app/shared/shared.module';
import { InterviewQuestionComponent } from './interview-question.component';
import { InterviewQuestionDetailComponent } from './interview-question-detail.component';
import { InterviewQuestionUpdateComponent } from './interview-question-update.component';
import { InterviewQuestionDeleteDialogComponent } from './interview-question-delete-dialog.component';
import { interviewQuestionRoute } from './interview-question.route';

@NgModule({
  imports: [InterviewdbSharedModule, RouterModule.forChild(interviewQuestionRoute)],
  declarations: [
    InterviewQuestionComponent,
    InterviewQuestionDetailComponent,
    InterviewQuestionUpdateComponent,
    InterviewQuestionDeleteDialogComponent,
  ],
  entryComponents: [InterviewQuestionDeleteDialogComponent],
})
export class InterviewdbInterviewQuestionModule {}
