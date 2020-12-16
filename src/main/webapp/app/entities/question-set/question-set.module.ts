import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InterviewdbSharedModule } from 'app/shared/shared.module';
import { QuestionSetComponent } from './question-set.component';
import { QuestionSetDetailComponent } from './question-set-detail.component';
import { QuestionSetUpdateComponent } from './question-set-update.component';
import { QuestionSetDeleteDialogComponent } from './question-set-delete-dialog.component';
import { questionSetRoute } from './question-set.route';

@NgModule({
  imports: [InterviewdbSharedModule, RouterModule.forChild(questionSetRoute)],
  declarations: [QuestionSetComponent, QuestionSetDetailComponent, QuestionSetUpdateComponent, QuestionSetDeleteDialogComponent],
  entryComponents: [QuestionSetDeleteDialogComponent],
})
export class InterviewdbQuestionSetModule {}
